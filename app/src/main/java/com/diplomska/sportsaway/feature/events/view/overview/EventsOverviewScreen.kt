package com.diplomska.sportsaway.feature.events.view.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
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
        ListOfGames(list = viewState.listOfNextMatches)
      }
    }
  }
}

@Composable
fun ListOfGames(list: List<Match>) {
//  LazyColumn {
//    itemsIndexed(list) { index, events ->
//      ImageTitleSubtitle(
//        title = "${events.homeTeam} - ${events.awayTeam}",
//        subtitle = events.date,
//        drawableStart = R.drawable.ic_soccer_ball,
//        //drawableEnd = Icons.Default.ArrowForward
//      )
//      if (index != list.lastIndex) {
//        ListDivider()
//      }
//    }
//    item {
//      Spacer(modifier = Modifier.width(30.dp))
//    }
//  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  EventsOverviewScreen()
}