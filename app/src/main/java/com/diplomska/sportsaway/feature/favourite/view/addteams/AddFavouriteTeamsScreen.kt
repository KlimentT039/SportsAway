package com.diplomska.sportsaway.feature.favourite.view.addteams

import ErrorScreen
import FavouriteTeamCard
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddFavouriteTeamsScreen(onBackClick: () -> Unit) {
  val viewModel = koinViewModel<AddFavouriteTeamsViewModel>()
  val viewState = viewModel.viewState.collectAsStateWithLifecycle()
  Scaffold.WithTopBarOnly(
    topBar = {
      AppBar.CustomTopAppBar(
        title = stringResource(R.string.add_favourite_teams_title),
        onBackClick = onBackClick,
        actionIcons = {
          AppBar.Icons.Custom(
            image = Icons.Default.Check,
            onClick = viewModel::onDoneClicked
          )
        }
      )
    },
    content = {
      AddFavouriteTeamsContent(
        modifier = Modifier.padding(it),
        viewState = viewState.value,
        onBackClick = onBackClick,
        onFavouriteClicked = viewModel::onFavouriteClicked,
        searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle().value,
        onSearchChanged = viewModel::onSearchValueChanged
      )
    }
  )
}

@Composable
private fun AddFavouriteTeamsContent(
  modifier: Modifier,
  viewState: ViewState,
  searchQuery: String,
  onSearchChanged: (String) -> Unit,
  onBackClick: () -> Unit,
  onFavouriteClicked: (Team) -> Unit
) {
  when (viewState) {
    is ViewState.Loading -> OverlayLoader()
    is ViewState.Error -> ErrorScreen(
      title = stringResource(R.string.something_went_wrong),
      buttonText = stringResource(R.string.generic_back),
      onClick = onBackClick
    )

    is ViewState.TeamsData -> {
      AppBar.SearchAppBar(searchQuery = searchQuery, onSearchChanged, onBackClick = {}) {
        TeamsList(
          modifier = modifier,
          teams = viewState.teams,
          onFavouriteClicked = onFavouriteClicked
        )
      }
    }
  }
}


@Composable
fun TeamsList(modifier: Modifier, teams: List<Team>, onFavouriteClicked: (Team) -> Unit) {
  LazyColumn {
    teams.forEachIndexed { index, team ->
      item {
        FavouriteTeamCard(
          team = team,
          modifier = modifier,
          onFavoriteClick = onFavouriteClicked
        )
      }
      if (index < teams.lastIndex) {
        item {
          ListDivider()
        }
      }
    }
  }
}
