package com.diplomska.sportsaway.feature.events.view.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FilterChip(
  text: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val backgroundColor = if (isSelected) Color(0xFF014421) else Color.White
  val textColor = if (isSelected) Color.White else Color.Black

  Box(
    modifier = Modifier
      .background(
        color = backgroundColor,
        shape = RoundedCornerShape(20.dp)
      )
      .clickable { onClick() }
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    Text(
      text = text,
      color = textColor,
      style = MaterialTheme.typography.bodyMedium
    )
  }
}

@Preview
@Composable
fun SelectedFilterChipPreview() {
  FilterChip("General", isSelected = true, onClick = {})
}

@Preview
@Composable
fun UnselectedFilterChipPreview() {
  FilterChip("Vip", isSelected = false, onClick = {})
}