package com.diplomska.sportsaway.feature.authentication.register.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.feature.authentication.register.domain.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.diplomska.sportsaway.feature.authentication.register.view.RegisterViewState.UserData
import com.diplomska.sportsaway.feature.authentication.register.view.RegisterViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class RegisterViewState {

  data object Loading : RegisterViewState()

  data class UserData(
    val email: String = "",
    val password: String = "",
    val username: String = ""
  ) : RegisterViewState()

  data object Error : RegisterViewState()
}

sealed class RegisterEvent {
  data object NavigateToDashboard : RegisterEvent()
}

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

  private val _viewState = MutableStateFlow<RegisterViewState>(UserData())
  val viewState = _viewState.asStateFlow()

  private val _event = MutableSharedFlow<RegisterEvent>()
  val event = _event.asSharedFlow()

  fun onEmailInputChanged(email: String) = updateViewStateWithData {
    it.copy(email = email)
  }

  fun onPasswordInputChanged(password: String) = updateViewStateWithData {
    it.copy(password = password)
  }

  fun onUsernameInputChanged(username: String) = updateViewStateWithData {
    it.copy(username = username)
  }

  fun onTryAgainClick() = viewModelScope.launch {
    _viewState.update { UserData() }
  }

  fun onSignUpClick() = viewModelScope.launch {
    val userInput = (_viewState.value as? UserData) ?: return@launch
    _viewState.update { RegisterViewState.Loading }
    registerUseCase(
      email = userInput.email,
      password = userInput.password,
      name = userInput.username
    ).fold(
      onFailure = {
        _viewState.update { RegisterViewState.Error }
      },
      onSuccess = {
        _event.emit(RegisterEvent.NavigateToDashboard)
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