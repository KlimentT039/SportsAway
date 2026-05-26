package com.diplomska.sportsaway.feature.favourite.view

sealed class FavouriteViewState {
  data object Loading : FavouriteViewState()
  data object HasNotLoggedIn : FavouriteViewState()
  data object ShowError : FavouriteViewState()
  data object HasNotSelectedTeams : FavouriteViewState()

  data class TeamsAndMatches(val favouriteTeams: List<Int>) : FavouriteViewState()
}
