package com.diplomska.sportsaway.feature.authentication.register.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.feature.authentication.login.domain.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.diplomska.sportsaway.feature.authentication.register.view.RegisterViewState.UserData
import com.diplomska.sportsaway.feature.authentication.register.view.RegisterViewState

sealed class RegisterViewState {

  data object Loading : RegisterViewState()

  data class UserData(
    val email: String = "",
    val password: String = "",
    val username: String = ""
  ) : RegisterViewState()
}

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

  private val _viewState = MutableStateFlow<RegisterViewState>(UserData())
  val viewState = _viewState.asStateFlow()

  fun onEmailInputChanged(email: String) = updateViewStateWithData {
    it.copy(email = email)
  }

  fun onPasswordInputChanged(password: String) = updateViewStateWithData {
    it.copy(password = password)
  }

  fun onUsernameInputChanged(username: String) = updateViewStateWithData {
    it.copy(username = username)
  }

  fun onSignUpClick() = viewModelScope.launch {
    val userInput = (_viewState.value as? UserData) ?: return@launch
    _viewState.update { RegisterViewState.Loading }
    registerUseCase(
      email = userInput.email,
      password = userInput.password,
      name = userInput.username
    ).fold(
      onFailure = {},
      onSuccess = {}
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