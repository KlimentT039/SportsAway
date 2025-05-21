package com.diplomska.sportsaway.feature.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.feature.profile.domain.GetUserDataUseCase
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState.Loading
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface ProfileEvents {
  data object NavigateToDashboard : ProfileEvents
}

class ProfileViewModel(private val getUserDataUseCase: GetUserDataUseCase) : ViewModel() {

  private val _viewState = MutableStateFlow<ProfileViewState>(Loading)
  val viewState = _viewState.asStateFlow()

  private val _event = MutableSharedFlow<ProfileEvents>()
  val event = _event.asSharedFlow()

  fun requestState() {
    viewModelScope.launch {
      getUserDataUseCase.getUserStatus().fold(
        onFailure = { _viewState.update { ProfileViewState.ShowError } },
        onSuccess = { successState -> _viewState.update { successState } }
      )
    }
  }

  fun onLogout() = viewModelScope.launch {
    _viewState.update { Loading }
    getUserDataUseCase.logout().fold(
      onFailure = { _viewState.update { ProfileViewState.ShowError } },
      onSuccess = { _event.emit(ProfileEvents.NavigateToDashboard) }
    )
  }
}