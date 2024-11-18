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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.diplomska.sportsaway.common.shared.model.getMatchDescription
import com.diplomska.sportsaway.common.shared.model.getMatchTitle
import com.diplomska.sportsaway.common.shared.model.initRandomGeneralTickets
import com.diplomska.sportsaway.common.shared.model.initRandomVipTickets
import com.diplomska.sportsaway.common.style.compose.components.CustomToolbar
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.typography
import com.diplomska.sportsaway.feature.events.view.model.TicketFilter

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
        modifier = Modifier.background(backgroundDefault),
        topBar = {
//          TopAppBar(
//            title = {
//              Text(
//                text = "${match.homeTeam.name} vs ${match.awayTeam.name}",
//                color = colorResource(id = R.color.topBarTextColor)
//              )
//            },
//            navigationIcon = {
//              IconButton(onClick = onBackClick) {
//                Icon(
//                  imageVector = Icons.Default.ArrowBack,
//                  contentDescription = "Back",
//                  tint = colorResource(id = R.color.topBarTextColor)
//                )
//              }
//            },
//            backgroundColor = colorResource(id = R.color.mainColor),
//            elevation = 8.dp
//          )
          CustomToolbar(
            title = match.getMatchTitle(),
            description = match.getMatchDescription(),
            backButtonIcon = Icons.Default.ArrowBack,
            onBackPressed = onBackClick
          )
        }
      ) { paddingValues ->
        CustomEventDetailsContent(
          state = state.value as EventDetailsViewState.EventData,
          onTicketSelected = { },
          modifier = Modifier.padding(paddingValues),
          onFilterClicked = viewModel::onTicketFilterSelected
        )
      }
    }
  }
}

@Composable
fun CustomEventDetailsContent(
  state: EventDetailsViewState.EventData,
  onTicketSelected: (Ticket) -> Unit,
  modifier: Modifier = Modifier,
  onFilterClicked: (TicketFilter) -> Unit
) {
  val match = state.match
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(color = colorResource(id = R.color.backgroundDefault))
  ) {
    // Top Section
    LazyColumn(
      modifier = Modifier
        .weight(1f) // Allow it to take required content height
        .fillMaxSize() // Ensure the LazyColumn takes up all available height
    ) {
      item { EventBanner(match) }
      item { Spacer(modifier = Modifier.height(16.dp)) }
    }

    // Bottom Section (Fills remaining space)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(backgroundSurface)
    ) {
      Column(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth()
      ) {
        Text(
          text = "Tickets",
          style = MaterialTheme.typography.h5.copy(
            fontSize = 22.sp,
            color = colorResource(id = R.color.typographyTextPrimary)
          ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        TicketFilters(
          selectedFilter = state.selectedFilter,
          onFilterSelected = onFilterClicked
        )

        Spacer(modifier = Modifier.height(8.dp))

        state.match.tickets.forEach { ticket ->
          TicketOption(
            ticket = ticket,
            onClick = { onTicketSelected(ticket) }
          )
        }

        BuyButton(onClick = {})
      }
    }
  }
}


@Composable
fun EventBanner(match: Match) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(250.dp)
      .padding(16.dp)
      .clip(RoundedCornerShape(16.dp))
  ) {
    if (!match.venueImage.isNullOrEmpty()) {
      AsyncImage(
        model = match.venueImage,
        contentDescription = null,
        modifier = Modifier.matchParentSize(),
        contentScale = ContentScale.Crop
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.stadium_map),
        contentDescription = "Event Banner",
        modifier = Modifier.matchParentSize(),
        contentScale = ContentScale.Crop
      )
    }

    // Gradient Overlay
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .align(Alignment.BottomCenter)
        .background(
          Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
          )
        )
    )

    // Text Content
    Text(
      text = match.venue.takeIf { match.venueImage != null } ?: "",
      style = MaterialTheme.typography.h4.copy(
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
      ),
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(16.dp)
    )
  }
}

@Composable
fun EventInfoSection(match: Match) {
  Card(
    shape = RoundedCornerShape(12.dp),
    backgroundColor = Color.White,
    elevation = 4.dp,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      if (match.venueImage == null) {
        Text(
          text = match.venue ?: "Unknown Venue",
          style = typography.mRegular.copy(
            fontWeight = FontWeight.Bold // Make the venue bold for emphasis
          )
        )
      }

      Text(
        text = "Date: ${match.date}",
        style = typography.sRegularSecundary
      )

      // Time
      Text(
        text = "Time: ${match.time.takeIf { it.isNotEmpty() } ?: "TBA"}",
        style = typography.sRegularSecundary
      )
    }
  }
}


@Composable
fun TicketOption(ticket: Ticket, onClick: (Ticket) -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .clickable { onClick(ticket) },
    elevation = 4.dp,
    shape = RoundedCornerShape(8.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = stringResource(ticket.title),
          style = typography.mRegular.copy(fontSize = 16.sp),
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Remaining: ${ticket.remainingTickets}",
          style = typography.xsRegular,
          color = Color.Gray
        )
      }
      Text(
        text = "$${ticket.price} each",
        style = typography.mRegular.copy(
          color = Color(0xFF2E7D32),
          fontSize = 14.sp
        ), // Green for emphasis
        fontWeight = FontWeight.Bold
      )
    }
  }
}


@Composable
fun BuyButton(onClick: () -> Unit) {
  Box(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth()
      .padding(vertical = 16.dp),
    contentAlignment = Alignment.Center
  ) {
    Button(
      onClick = onClick,
      shape = RoundedCornerShape(24.dp),
      colors = ButtonDefaults.buttonColors(backgroundColor = mainColor), // Your app's green color
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "Buy Tickets",
        style = MaterialTheme.typography.button,
        color = Color.White,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

@Composable
fun TicketFilters(selectedFilter: TicketFilter?, onFilterSelected: (TicketFilter) -> Unit) {
  val filters = TicketFilter.entries
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp, horizontal = 16.dp),
    horizontalArrangement = Arrangement.Center
  ) {
    filters.forEach { filter ->
      FilterChip(
        text = filter.displayName,
        isSelected = filter == selectedFilter,
        onClick = { onFilterSelected(filter) }
      )
      Spacer(Modifier.width(16.dp))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CustomEventDetailsPreview() {
  CustomEventDetailsContent(
    state = EventDetailsViewState.EventData(
      Match(
        tickets = listOf(
          initRandomGeneralTickets(0, false),
          initRandomVipTickets(0)
        )
      ),
      selectedFilter = TicketFilter.VIP
    ),
    onTicketSelected = {},
    onFilterClicked = {}
  )
}
