package com.diplomska.sportsaway.feature.dashboard.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.common.style.compose.layouts.SectionWithHeader
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.feature.events.view.details.EventDetailsActivity
import com.diplomska.sportsaway.common.shared.model.Competition
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.utils.chunkedList
import com.diplomska.sportsaway.common.style.compose.components.MatchCard
import com.diplomska.sportsaway.common.style.compose.components.TileWithIconAndText
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
  val viewModel = koinViewModel<HomeViewModel>()
  val viewState = viewModel.viewState.collectAsState()
  HomeContent(viewState = viewState.value)
}

@Composable
private fun HomeContent(viewState: ViewState) {
  when (viewState) {
    is ViewState.Loading -> OverlayLoader()
    is ViewState.HomeData -> {
      LazyColumn {
        item {
          ListOfGames(viewState.matchData.listOfNextMatches)
        }
        item {
          Spacer(modifier = Modifier.height(32.dp))
        }

        item {
          ListOfCompetitions(list = viewState.competitionsData.listOfCompetitions)
        }
      }
    }
  }
}

@Composable
fun ListOfGames(list: List<Match>) {
  val context = LocalContext.current

  SectionWithHeader(title = R.string.trending_events, endWord = R.string.see_more) {
    LazyRow(modifier = Modifier.padding(horizontal = 8.dp)) {
      itemsIndexed(chunkedList(list, 2)) { index, columnEvents ->
        Column(modifier = Modifier.fillParentMaxWidth(0.8f)) {
          columnEvents.forEachIndexed { index, listItem ->
            MatchCard(
              match = listItem,
              onClick = {
                context.startActivity(
                  EventDetailsActivity.createIntent(
                    context,
                    listItem
                  )
                )
              }
            )
          }
        }
      }
    }
  }
}

@Composable
fun ListOfCompetitions(list: List<Competition>) {
  SectionWithHeader(title = R.string.leagues) {
    LazyRow(modifier = Modifier.padding(horizontal = 8.dp)) {
      items(list) {
        TileWithIconAndText(imageRes = it.emblem, text = it.name)
      }
    }
  }
}


@Preview
@Composable
fun PreviewHomeContent() {
  HomeContent(viewState = ViewState.HomeData())
}