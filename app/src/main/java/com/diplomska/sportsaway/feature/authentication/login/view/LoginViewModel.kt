package com.diplomska.sportsaway.feature.authentication.login.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.feature.authentication.login.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.diplomska.sportsaway.feature.authentication.login.view.LoginViewState.UserData
import com.diplomska.sportsaway.feature.authentication.login.view.LoginViewState.Loading
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class LoginViewState {

  data object Loading : LoginViewState()

  data class UserData(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true
  ) : LoginViewState()

}

sealed class LoginEvents {
  data object LoginFailed : LoginEvents()
  data object SuccessfulLogin : LoginEvents()
}

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

  private val _viewState = MutableStateFlow<LoginViewState>(UserData())
  val viewState = _viewState.asStateFlow()

  private val _event = MutableSharedFlow<LoginEvents>()
  val event = _event.asSharedFlow()

  fun onEmailInputChanged(email: String) = updateViewStateWithData {
    it.copy(email = email)
  }

  fun onPasswordInputChanged(password: String) = updateViewStateWithData {
    it.copy(password = password, isPasswordValid = loginUseCase.validatePassword(password))
  }

  fun onLoginClick() = beginLoginProcess()

  fun onTryAgainLogin() = beginLoginProcess()

  private fun beginLoginProcess() = viewModelScope.launch {
    val userInput = (_viewState.value as? UserData) ?: return@launch
    if (!loginUseCase.validateEmail(email = userInput.email)) {
      updateViewStateWithData { state ->
        state.copy(isEmailValid = false)
      }
      return@launch
    }

    _viewState.update { Loading }

    loginUseCase(email = userInput.email, password = userInput.password).fold(
      onFailure = {
        _event.emit(LoginEvents.LoginFailed)
      },
      onSuccess = {
        _event.emit(LoginEvents.SuccessfulLogin)
      }
    )
  }

  private inline fun updateViewStateWithData(block: (UserData) -> UserData) {
    _viewState.update { viewState ->
      if (viewState is UserData) {
        block(viewState)
      } else {
        viewState
      }
    }
  }
}