package com.diplomska.sportsaway.feature.events.view.overview

import ErrorScreen
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.errorhandling.view.GeneralErrorActivity
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.components.MatchItem
import com.diplomska.sportsaway.common.style.compose.components.MatchSection
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.feature.events.view.details.EventDetailsActivity
import com.diplomska.sportsaway.feature.events.view.model.GroupedMatch
import com.diplomska.sportsaway.feature.favourite.view.AccessDeniedScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EventsOverviewScreen(competitionId: Int? = null) {
  val viewModel = koinViewModel<EventsOverviewViewModel> {
    parametersOf(competitionId)
  }
  val viewState = viewModel.viewState.collectAsState()
  val searchQuery = viewModel.searchQuery.collectAsState()
  EventsContent(
    viewState = viewState.value,
    onTryAgain = { viewModel.fetchNextMatches(competitionId) },
    searchQuery = searchQuery.value,
    onSearchQuery = viewModel::onSearchQuery
  )
}

@Composable
private fun EventsContent(
  viewState: EventsViewState,
  onTryAgain: () -> Unit,
  searchQuery: String,
  onSearchQuery: (String) -> Unit
) {
  when (viewState) {
    is EventsViewState.Loading -> OverlayLoader()
    is EventsViewState.EmptySearchResult -> AccessDeniedScreen(
      message = stringResource(id = R.string.no_results),
      buttonText = null
    )

    is EventsViewState.Error -> ErrorScreen(
      title = "Something gone wrong",
      description = "User cannot be created at the moment",
      onClick = onTryAgain
    )

    is EventsViewState.EventData -> {
      AppBar.SearchAppBar(
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQuery
      ) {
        ListOfGames(
          list = viewState.groupedMatches
        )
      }
    }
  }
}

@Composable
fun ListOfGames(list: List<GroupedMatch>) {
  val context = LocalContext.current
  val visibilityMap = remember { mutableStateMapOf<String, Boolean>() }
  LazyColumn {
    itemsIndexed(list) { index, league ->
      val competition = league.competition.name
      key("matchSection-${competition}-$index") {
        val isContentVisible = visibilityMap[competition] ?: true
        MatchSection(competition = league.competition, isContentVisible = isContentVisible,
          onVisibilityChange = { visibility -> visibilityMap[competition] = visibility }) {
          league.matches.forEachIndexed { matchIndex, match ->
            MatchItem(
              match = match,
              onClick = {
                context.startActivity(EventDetailsActivity.createIntent(context, match))
              })
            if (matchIndex != league.matches.lastIndex)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  EventsOverviewScreen()
}