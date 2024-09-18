package com.diplomska.sportsaway.common.style.compose.layouts

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.typography

@Composable
fun TicketForm(ticket: Ticket, isSelected: Boolean, onClick: () -> Unit) {
  // Use animated color for background and border when selected
  val borderColor by animateColorAsState(if (isSelected) mainColor else Color.Gray, label = "")
  val backgroundColor by animateColorAsState(
    if (isSelected) backgroundSurface else Color.White,
    label = ""
  )

  Card(
    modifier = Modifier
      .padding(12.dp)
      .fillMaxWidth()
      .clickable(onClick = onClick),
    elevation = 6.dp,
    shape = RoundedCornerShape(10.dp),
    border = BorderStroke(1.dp, borderColor),
    backgroundColor = backgroundColor
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Ticket info column
      Column(
        modifier = Modifier
          .weight(1f)
      ) {
        Text(
          text = stringResource(id = ticket.title),
          style = MaterialTheme.typography.h6,
          color = if (isSelected) mainColor else MaterialTheme.colors.onSurface,
          modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
          text = "Remaining Tickets: ${ticket.remainingTickets}",
          style = MaterialTheme.typography.body2,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )
      }

      // Price display column
      Text(
        text = "$${ticket.price}",
        style = MaterialTheme.typography.h5,
        color = mainColor,
        modifier = Modifier.padding(start = 16.dp)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewTicketLayout() {
  TicketForm(ticket = Ticket(R.string.general_ticket, 10, 30, matchId = 123),
    isSelected = true,
    onClick = {}
  )
}

