package com.diplomska.sportsaway.feature.events.view.overview

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.components.MatchItem
import com.diplomska.sportsaway.common.style.compose.components.MatchSection
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.feature.events.view.model.GroupedMatch
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventsOverviewScreen() {
  val viewModel = koinViewModel<EventsOverviewViewModel>()
  val viewState = viewModel.nextMatchesState.collectAsState()
  EventsContent(viewState = viewState.value, viewModel::onSearchQuery)
}

@Composable
private fun EventsContent(viewState: EventsViewState, onSearchQuery: (String) -> Unit) {
  when (viewState) {
    is EventsViewState.Loading -> OverlayLoader()
    is EventsViewState.EventData -> {
      AppBar.SearchAppBar(searchQuery = "", onSearchQueryChange = onSearchQuery) {
        ListOfGames(list = viewState.groupedMatches)
      }
    }
  }
}

@Composable
fun ListOfGames(list: List<GroupedMatch>) {
  val visibilityMap = remember { mutableStateMapOf<String, Boolean>() }
  LazyColumn {
    itemsIndexed(list) { index, league ->
      val competition = league.competition.name
      key("matchSection-${competition}-$index") {
        val isContentVisible = visibilityMap[competition] ?: true
        MatchSection(competition = league.competition, isContentVisible = isContentVisible,
          onVisibilityChange = { visibility -> visibilityMap[competition] = visibility }) {
          league.matches.forEachIndexed { matchIndex, match ->
            MatchItem(match = match)
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