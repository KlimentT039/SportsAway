package com.diplomska.sportsaway.feature.events.view.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.style.compose.components.CustomToolbar
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.layouts.TicketForm
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault

@Composable
fun EventDetailsScreen(onBackClick: () -> Unit, viewModel: EventDetailsViewModel) {
  val state = viewModel.state.collectAsStateWithLifecycle()
  when (state.value) {
    is EventDetailsViewState.Loading -> OverlayLoader()
    is EventDetailsViewState.EventData -> {
      val match = (state.value as EventDetailsViewState.EventData).match
      Scaffold.WithTopBarOnly(
        topBar = {
          CustomToolbar(
            title = "${match.homeTeam.shortName} - ${match.awayTeam.shortName}",
            description = getDateAndVenue(match),
            onBackPressed = onBackClick,
            backButtonIcon = Icons.Filled.ArrowBack,
          )
        },
        containerColor = backgroundDefault,
        content = {
          EventDetailsContent(match)
        },
      )
    }
  }
}

@Composable
fun EventDetailsContent(match: Match) {
  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      Image(
        painter = painterResource(id = R.drawable.stadium_map),
        contentDescription = null,
        modifier = Modifier
          .fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    }
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        item {
          Column(
            modifier = Modifier
              .background(backgroundDefault)
              .fillMaxWidth()
          ) {
            Tickets(match)
          }
        }
      }
    }
  }
}


@Composable
fun Tickets(match: Match) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp)
  ) {
    match.tickets.forEachIndexed { index, ticket ->
      TicketForm(ticket = ticket, isSelected = false) {}
      if (index < match.tickets.size - 1) {
        ListDivider()
      }
    }
  }
}

private fun getDateAndVenue(match: Match): String {
  val venue = "${match.venue} - ".takeIf { !match.venue.isNullOrEmpty() }
  val date = match.date.takeIf { match.date.isNotEmpty() }
  return "${venue.takeIf { it != null }}$date"
}