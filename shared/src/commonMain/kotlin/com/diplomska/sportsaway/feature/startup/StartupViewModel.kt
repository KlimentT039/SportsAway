package com.diplomska.sportsaway.feature.startup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface StartupViewState {
  data object Loading : StartupViewState
  data class Ready(val isLoggedIn: Boolean) : StartupViewState
}

/**
 * Resolves what the app should show after the brand reveal.
 * Owned by both platforms — Android uses it from the splash activity,
 * iOS uses it from the root scene to gate the tab view.
 */
class StartupViewModel(
  private val authRepository: AuthRepository
) : ViewModel() {

  private val _viewState = MutableStateFlow<StartupViewState>(StartupViewState.Loading)
  val viewState = _viewState.asStateFlow()

  fun resolve() = viewModelScope.launch {
    delay(MIN_BRAND_REVEAL_MS)
    _viewState.value = StartupViewState.Ready(isLoggedIn = authRepository.isLogged())
  }

  companion object {
    private const val MIN_BRAND_REVEAL_MS = 1500L
  }
}
