package com.diplomska.sportsaway.feature.favourite.view.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.common.style.compose.typography
import com.diplomska.sportsaway.data.events_data.model.Player

@Composable
fun PlayerItem(player: Player) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = player.name,
      style = typography.mRegular,
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = "Position: ${player.position}")
    Text(text = "Date of Birth: ${player.dateOfBirth}")
    Text(text = "Nationality: ${player.nationality}")
  }
}