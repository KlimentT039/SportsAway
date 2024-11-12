package com.diplomska.sportsaway.feature.events.view.details

import ErrorScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Match

import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventDetailsScreen(
  onBackClick: () -> Unit,
  matchId: Int,
) {
  val viewModel = koinViewModel<EventDetailsViewModel>()
  val state = viewModel.state.collectAsStateWithLifecycle()
  when (state.value) {
    is EventDetailsViewState.Loading -> OverlayLoader()
    is EventDetailsViewState.ShowError -> ErrorScreen(
      title = stringResource(R.string.try_again),
      onClick = { viewModel.initData(matchId) }
    )

    is EventDetailsViewState.EventData -> {
      val match = (state.value as EventDetailsViewState.EventData).match
      Scaffold(
        topBar = {
          TopAppBar(
            title = {
              Text(
                text = "${match.homeTeam.name} vs ${match.awayTeam.name}",
                color = Color.White
              )
            },
            navigationIcon = {
              IconButton(onClick = onBackClick) {
                Icon(
                  imageVector = Icons.Default.ArrowBack,
                  contentDescription = "Back",
                  tint = Color.White
                )
              }
            },
            backgroundColor = colorResource(R.color.mainColor),
            elevation = 8.dp
          )
        }
      ) { paddingValues ->
        CustomEventDetailsContent(
          match = match,
          onTicketSelected = { },
          modifier = Modifier.padding(paddingValues)
        )
      }
    }
  }
}

@Composable
fun CustomEventDetailsContent(
  match: Match,
  onTicketSelected: (Ticket) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(color = colorResource(R.color.backgroundDefault))
      .padding(16.dp)
  ) {
    EventBanner()

    Spacer(modifier = Modifier.height(16.dp))

    EventInfoSection(match = match)

    Spacer(modifier = Modifier.height(24.dp))

    Text(
      text = "Tickets",
      style = MaterialTheme.typography.h6.copy(fontSize = 20.sp),
      color = colorResource(R.color.mainColor)
    )

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(match.tickets) { ticket ->
        TicketOption(
          ticket = ticket,
          onTicketSelected = { onTicketSelected(ticket) }
        )
      }
    }
  }
}

@Composable
fun EventBanner() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(220.dp)
      .clip(RoundedCornerShape(12.dp))
  ) {
    Image(
      painter = painterResource(id = R.drawable.stadium_map),
      contentDescription = "Event Banner",
      modifier = Modifier.matchParentSize(),
      contentScale = ContentScale.Crop
    )
    Box(
      modifier = Modifier
        .matchParentSize()
        .background(
          Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
            startY = 100f
          )
        )
    )
  }
}

@Composable
fun EventInfoSection(match: Match) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    Text(
      text = match.venue ?: "",
      style = typography.mRegular,
      color = colorResource(R.color.typographyTextPrimary)
    )

    Text(
      text = "Date: ${match.date}",
      style = typography.sRegularPrimary,
      color = colorResource(R.color.typographyTextPrimary).copy(alpha = 0.7f)
    )

    Text(
      text = "Time: ${match.time.takeIf { it.isNotEmpty() } ?: "TBA"}",
      style = typography.sRegularPrimary,
      color = colorResource(R.color.typographyTextPrimary).copy(alpha = 0.7f)
    )
  }
}

@Composable
fun TicketOption(ticket: Ticket, onTicketSelected: () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onTicketSelected)
      .padding(horizontal = 8.dp, vertical = 4.dp),
    shape = RoundedCornerShape(12.dp),
    elevation = 4.dp,
    backgroundColor = colorResource(R.color.cardBackground)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = stringResource(ticket.title),
          style = typography.mRegular,
          color = colorResource(R.color.typographyTextPrimary)
        )
        Text(
          text = "Remaining: ${ticket.remainingTickets}",
          style = typography.sRegularPrimary,
          color = colorResource(R.color.typographyTextPrimary).copy(alpha = 0.6f)
        )
      }
      Text(
        text = "$${ticket.price}",
        style = typography.xsRegular.copy(color = colorResource(R.color.priceColor)),
        modifier = Modifier.padding(start = 8.dp)
      )
    }
  }
}

