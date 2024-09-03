package com.diplomska.sportsaway.feature.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private val _viewState = MutableStateFlow<ProfileViewState>(Loading)
  val viewState = _viewState.asStateFlow()

  init {
    viewModelScope.launch {
      if (!authRepository.isLogged()) {
        _viewState.update { ProfileViewState.UserHasNotLoggedIn }
      } else {
        authRepository.getCurrentUser().fold(
          onFailure = {},
          onSuccess = { user ->
            _viewState.update { ProfileViewState.ProfileData(user = user) }
          }
        )
      }
    }
  }
}