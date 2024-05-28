package com.diplomska.sportsaway.dashboard.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.dashboard.home.components.tiles.SportTilesContent
import com.diplomska.sportsaway.dashboard.home.components.tiles.model.SportsTile
import com.diplomska.sportsaway.dashboard.home.components.tiles.model.getSportsTiles
import com.diplomska.sportsaway.style.compose.layouts.SectionWithHeader
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.events.model.Match
import com.diplomska.sportsaway.events.view.details.EventDetailsActivity
import com.diplomska.sportsaway.shared.utils.chunkedList
import com.diplomska.sportsaway.style.compose.components.ImageTitleSubtitle
import com.diplomska.sportsaway.style.compose.components.ListDivider
import com.diplomska.sportsaway.style.compose.layouts.OverlayLoader
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
  val viewModel = koinViewModel<HomeViewModel>()
  val viewState = viewModel.nextMatchesState.collectAsState()
  HomeContent(viewState = viewState.value)
}

@Composable
private fun HomeContent(viewState: ViewState) {
  when (viewState) {
    is ViewState.Loading -> OverlayLoader()
    is ViewState.HomeData -> {
      Column {
        ListOfGames(viewState.listOfNextMatches)
        Spacer(modifier = Modifier.height(32.dp))
        SportsTilesList(list = getSportsTiles())
      }
    }
  }
}

@Composable
fun ListOfGames(list: List<Match>) {
  val context = LocalContext.current

  SectionWithHeader(title = R.string.trending_events, endWord = R.string.see_more) {
    LazyRow {
      items(chunkedList(list, 3)) { columnEvents ->
        Column(modifier = Modifier.fillParentMaxWidth(0.8f)) {
          columnEvents.forEachIndexed { index, listItem ->
            ImageTitleSubtitle(
              title = "${listItem.homeTeam} - ${listItem.awayTeam}",
              subtitle = listItem.date,
              drawableStart = listItem.iconRes,
              drawableEnd = Icons.Default.ArrowForward,
              onClick = {
                context.startActivity(EventDetailsActivity.createIntent(context, listItem))
              }
            )
            if (index < columnEvents.lastIndex) {
              ListDivider()
            }
          }
        }
      }
      item {
        Spacer(modifier = Modifier.width(30.dp))
      }
    }
  }
}

@Composable
fun SportsTilesList(list: List<SportsTile>) {
  SectionWithHeader(title = R.string.browse_by_category) {
    SportTilesContent(list = list)
  }
}


@Preview
@Composable
fun PreviewHomeContent() {
  HomeContent(viewState = ViewState.HomeData())
}