package com.diplomska.sportsaway.feature.favourite.view.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.diplomska.sportsaway.feature.favourite.view.AccessDeniedScreen
import com.diplomska.sportsaway.feature.favourite.view.AddFirstTeamScreen
import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewModel
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteScreen() {
  val viewModel = koinViewModel<FavouriteViewModel>()
  val viewState = viewModel.viewState.collectAsState()
  FavouriteContent(viewState = viewState.value)

}

@Composable
fun FavouriteContent(viewState: UserFavouriteState) {
  when (viewState) {
    UserFavouriteState.Loading -> OverlayLoader()
    UserFavouriteState.UserHasNotLoggedIn -> AccessDeniedScreen()
    UserFavouriteState.UserHasNotSelectedTeams -> AddFirstTeamScreen()
    else -> {}
  }

}