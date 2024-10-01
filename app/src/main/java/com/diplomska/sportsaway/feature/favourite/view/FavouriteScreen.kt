package com.diplomska.sportsaway.feature.favourite.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.feature.favourite.view.model.UserFavouriteState
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
    UserFavouriteState.UserHasNotLoggedIn -> AccessDeniedScreen(
      stringResource(id = R.string.access_denied_favourites),
      buttonText = stringResource(id = R.string.log_in),
    )

    UserFavouriteState.UserHasNotSelectedTeams -> AddFirstTeamScreen()
    else -> {}
  }

}