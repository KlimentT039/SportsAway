package com.diplomska.sportsaway.feature.favourite.view.model

sealed class UserFavouriteState {
  data object Loading: UserFavouriteState()
  data object UserHasNotLoggedIn : UserFavouriteState()
  data object UserHasNotSelectedTeams : UserFavouriteState()
  data class FavouriteTeams(val list: List<String>) : UserFavouriteState()
}