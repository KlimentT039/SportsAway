package com.diplomska.sportsaway.feature.dashboard.home.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Competition
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.components.MatchCard
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.components.TileWithIconAndText
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.layouts.SectionWithHeader
import com.diplomska.sportsaway.feature.events.view.details.EventDetailsActivity
import com.diplomska.sportsaway.feature.events.view.overview.EventOverviewActivity
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
  val viewModel = koinViewModel<HomeViewModel>()
  val viewState = viewModel.viewState.collectAsState()
  Scaffold.WithTopBarOnly(
    topBar = {
      AppBar.HomeAppBar(
        title = stringResource(id = R.string.app_name),
        icon = R.drawable.ic_logo
      )
    },
    content = { HomeContent(viewState = viewState.value) })
}

@Composable
private fun HomeContent(viewState: ViewState) {
  val context = LocalContext.current
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
          ListOfCompetitions(list = viewState.competitionsData.listOfCompetitions, context)
        }
      }
    }
  }
}

@Composable
fun ListOfGames(list: List<Match>) {
  val context = LocalContext.current

  SectionWithHeader(
    title = R.string.trending_events,
    endWord = R.string.see_more,
    onEndWordClick = {
      context.startActivity(EventOverviewActivity.createIntent(context, null))
    }
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      list.take(3).forEach { match ->
        MatchCard.Featured(
          match = match,
          modifier = Modifier.fillMaxWidth(),
          onClick = {
            context.startActivity(EventDetailsActivity.createIntent(context, match.id))
          }
        )
      }
    }
  }
}

@Composable
fun ListOfCompetitions(list: List<Competition>, context: Context) {
  SectionWithHeader(title = R.string.leagues) {
    LazyRow(modifier = Modifier.padding(horizontal = 8.dp)) {
      items(list) {
        TileWithIconAndText(
          imageRes = it.emblem,
          text = it.name,
          onClick = { context.startActivity(EventOverviewActivity.createIntent(context, it.id)) }
        )
      }
    }
  }
}


@Preview
@Composable
fun PreviewHomeContent() {
  HomeContent(viewState = ViewState.HomeData())
}