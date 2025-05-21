package com.diplomska.sportsaway.feature.authentication.login.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.feature.authentication.login.domain.LoginUseCase
import com.diplomska.sportsaway.feature.authentication.login.view.LoginViewState.UserData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class LoginViewState {
  data object LoginFailed : LoginViewState()

  data class UserData(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val wrongCredentials: Boolean = false,
    val isButtonEnabled: Boolean = false
  ) : LoginViewState()

}

sealed class LoginEvents {
  data object SuccessfulLogin : LoginEvents()
  data object NavigateToRegisterActivity : LoginEvents()
}

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

  private val _viewState = MutableStateFlow<LoginViewState>(UserData())
  val viewState = _viewState.asStateFlow()

  private val _event = MutableSharedFlow<LoginEvents>()
  val event = _event.asSharedFlow()

  fun onEmailInputChanged(email: String) = updateViewStateWithData {
    it.copy(email = email)
  }

  fun onPasswordInputChanged(password: String) {
    updateViewStateWithData {
      it.copy(password = password, isPasswordValid = loginUseCase.validatePassword(password))
    }
    updateButtonState()
  }

  fun navigateToRegisterActivity() = viewModelScope.launch {
    _event.emit(LoginEvents.NavigateToRegisterActivity)
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

    updateViewStateWithData { it.copy(isLoading = true) }

    loginUseCase(email = userInput.email, password = userInput.password).fold(
      onFailure = { baseError ->
        if (baseError is BaseError.AuthenticationError) {
          updateViewStateWithData { it.copy(wrongCredentials = true, isLoading = false ) }
        } else {
          _viewState.update { LoginViewState.LoginFailed }
        }
      },
      onSuccess = {
        _event.emit(LoginEvents.SuccessfulLogin)
      }
    )
    updateButtonState()
  }

  fun onDismissError(){
    updateViewStateWithData { state ->
      state.copy(wrongCredentials = false)
    }
  }

  private fun updateButtonState() {
    updateViewStateWithData { state ->
      state.copy(isButtonEnabled = state.isEmailValid && state.isPasswordValid)
    }
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