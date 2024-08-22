package com.diplomska.sportsaway.feature.profile.model

import com.diplomska.sportsaway.data.authentication_data.model.User


sealed interface ProfileViewState {

  data object Loading : ProfileViewState

  data object UserHasNotLoggedIn : ProfileViewState

  data class ProfileData(
    val user: User
  ) : ProfileViewState

}


