package com.diplomska.sportsaway.feature.events.view.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
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
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.layouts.TicketForm
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.mainColor

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
            onBackPressed = onBackClick,
            backButtonIcon = Icons.Filled.ArrowBack,
          )
        },
        containerColor = mainColor,
        content = {
          EventDetailsContent(match)
        },
      )
    }
  }
}

@Composable
fun EventDetailsContent(match: Match) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundDefault)
  ) {
    Column(Modifier.fillMaxSize()) {
      Column(
        modifier = Modifier
          .fillMaxHeight(0.4f)
          .background(mainColor)
      ) {
        // Image background
        Image(
          painter = painterResource(id = R.drawable.soccer_stadium),
          contentDescription = null,
          modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
          contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
      }

      Column(
        Modifier
          .padding(16.dp)
          .background(backgroundDefault)
          .fillMaxHeight()
          .fillMaxWidth()
      ) {
      Tickets(match)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
          onClick = { /* Perform action for purchasing tickets */ },
          modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp)
            .background(mainColor)
        ) {
          Text(text = "Buy Tickets")
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
    TicketForm(match.generalTicket, true) {}
    TicketForm(match.vipTicket, false) {}
  }
}