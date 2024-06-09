package com.diplomska.sportsaway.events.view.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.shared.model.Match
import com.diplomska.sportsaway.style.compose.components.CustomToolbar
import com.diplomska.sportsaway.style.compose.components.Scaffold
import com.diplomska.sportsaway.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.style.compose.layouts.TicketForm
import com.diplomska.sportsaway.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.style.compose.theme.mainColor

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
            title = "${match.homeTeam} - ${match.awayTeam}",
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
      .background(mainColor)
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
//        Tickets(match)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
          onClick = { /* Perform action for purchasing tickets */ },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
        ) {
          Text(text = "Buy Tickets")
        }
      }
    }
  }
}
//
//@Composable
//fun Tickets(match: Match) {
//  Column(
//    modifier = Modifier
//      .fillMaxWidth()
//      .padding(vertical = 8.dp)
//  ) {
//    TicketForm(match.generalTickets, false) {}
//    TicketForm(match.vipTickets, false) {}
//  }
//}
//
//@Composable
//@Preview
//fun EventDetailsScreenPreview() {
//  EventDetailsContent(match = Match())
//}