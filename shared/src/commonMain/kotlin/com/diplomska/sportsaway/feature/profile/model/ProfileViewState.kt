package com.diplomska.sportsaway.feature.profile.model

sealed interface ProfileViewState {

  data object Loading : ProfileViewState

  data object UserHasNotLoggedIn : ProfileViewState

  data object ShowError : ProfileViewState

  data class ProfileData(
    val user: UserData
  ) : ProfileViewState
}
