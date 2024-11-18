package com.diplomska.sportsaway.feature.favourite.view

import ErrorScreen
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.components.MatchItem
import com.diplomska.sportsaway.common.style.compose.components.MatchSection
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.feature.authentication.login.view.LoginActivity
import com.diplomska.sportsaway.feature.events.view.details.EventDetailsActivity
import com.diplomska.sportsaway.feature.favourite.model.FavouriteTeam
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteScreen() {
  val viewModel = koinViewModel<FavouriteViewModel>()
  val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
  val favouriteTeams = viewModel.favouriteTeams.collectAsStateWithLifecycle().value
  Scaffold.WithTopBarOnly(
    topBar = {
      AppBar.CustomTopAppBar(
        title = stringResource(R.string.favourites),
        showBackButton = false,
        onBackClick = {},
        actionIcons = {
          AppBar.Icons.Custom(image = Icons.Default.Edit, onClick = {})
        }
      )
    },
    content = {
      FavouriteContent(
        viewState = viewState,
        favouriteTeams = favouriteTeams,
        onTryAgainClicked = viewModel::initScreen
      )
    }
  )
}

@Composable
fun FavouriteContent(
  viewState: FavouriteViewState,
  favouriteTeams: List<FavouriteTeam>,
  onTryAgainClicked: () -> Unit,
) {
  val context = LocalContext.current
  when (viewState) {
    is FavouriteViewState.Loading -> OverlayLoader()
    is FavouriteViewState.HasNotLoggedIn -> AccessDeniedScreen(
      stringResource(id = R.string.access_denied_favourites),
      buttonText = stringResource(id = R.string.log_in),
      onButtonClicked = { context.startActivity(LoginActivity.createIntent(context)) }
    )

    is FavouriteViewState.HasNotSelectedTeams -> NoTeamScreen()
    is FavouriteViewState.TeamsAndMatches -> {
      ListOfFavouriteTeamsAndMatches(favouriteTeams)
    }

    is FavouriteViewState.ShowError -> {
      ErrorScreen(
        title = stringResource(R.string.something_went_wrong),
        onClick = onTryAgainClicked
      )
    }
  }
}

@Composable
private fun ListOfFavouriteTeamsAndMatches(teams: List<FavouriteTeam>) {
  val context = LocalContext.current
  val visibilityMap = remember { mutableStateMapOf<String, Boolean>() }
  LazyColumn {
    itemsIndexed(teams) { index, team ->
      val favouriteTeam = team.favouriteTeam.name
      key("matchSection-${team}-$index") {
        val isContentVisible = visibilityMap[favouriteTeam] ?: true
        MatchSection(
          imageUrl = team.favouriteTeam.crest,
          name = favouriteTeam,
          isContentVisible = isContentVisible,
          onVisibilityChange = { visibility -> visibilityMap[favouriteTeam] = visibility }
        ) {
          team.matches.forEachIndexed { matchIndex, match ->
            MatchItem(
              match = match,
              onClick = {
                context.startActivity(EventDetailsActivity.createIntent(context, match.id))
              })
            if (matchIndex != team.matches.lastIndex)
              ListDivider()
          }
        }
      }
    }
    item {
      Spacer(modifier = Modifier.height(10.dp))
    }
  }
}