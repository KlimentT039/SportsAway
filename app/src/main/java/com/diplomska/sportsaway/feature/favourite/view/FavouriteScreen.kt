package com.diplomska.sportsaway.feature.favourite.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.feature.favourite.view.addteams.FavouriteEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteScreen() {
  val viewModel = koinViewModel<FavouriteViewModel>()
  val viewState = viewModel.viewState.collectAsState()
  FavouriteContent(viewState = viewState.value)

}

@Composable
fun FavouriteContent(viewState: FavouriteViewState) {
  when (viewState) {
    FavouriteViewState.Loading -> OverlayLoader()
    FavouriteViewState.HasNotLoggedIn -> AccessDeniedScreen(
      stringResource(id = R.string.access_denied_favourites),
      buttonText = stringResource(id = R.string.log_in),
    )

    FavouriteViewState.HasNotSelectedTeams -> NoTeamScreen()
    else -> {}
  }

}

fun FavouriteEvent() {

}