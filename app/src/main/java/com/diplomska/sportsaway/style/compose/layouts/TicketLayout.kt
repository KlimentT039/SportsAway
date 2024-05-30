package com.diplomska.sportsaway.style.compose.layouts

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
import com.diplomska.sportsaway.events.model.Ticket
import com.diplomska.sportsaway.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.style.compose.theme.mainColor
import com.diplomska.style.compose.typography

@Composable
fun TicketForm(ticketResponse: Ticket, isSelected: Boolean, onClick: () -> Unit) {
  val borderColor by animateColorAsState(if (isSelected) mainColor else Color.Gray, label = "")

  Card(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth()
      .background(backgroundSurface)
      .clickable(onClick = onClick),
    elevation = 4.dp,
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(2.dp, borderColor)
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = stringResource(id = ticketResponse.title),
          style = MaterialTheme.typography.h5,
          modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
          text = "Remaining Tickets: ${ticketResponse.remainingTickets}",
          style = MaterialTheme.typography.caption
        )
      }
      Text(
        text = "$${ticketResponse.price}",
        style = typography.mRegular,
        modifier = Modifier.align(Alignment.CenterVertically)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewTicketLayout() {
  TicketForm(ticketResponse = Ticket(R.string.general_ticket, 10, 30),
    isSelected = true,
    onClick = {}
  )
}

