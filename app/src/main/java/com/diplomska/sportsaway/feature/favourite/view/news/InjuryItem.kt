package com.diplomska.sportsaway.feature.favourite.view.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.common.style.compose.typography
import com.diplomska.sportsaway.data.events_data.model.InjuryReport

@Composable
fun InjuryItem(report: InjuryReport) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Text(
      text = report.player,
      style = typography.mRegular,
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Injury: ${report.injury}",
      style = typography.sRegularPrimary
    )
    Text(
      text = "Expected Return: ${report.expectedReturn}",
      style = typography.xsRegular
    )
    Text(
      text = "Status: ${report.status}",
      style = typography.xsRegular,
    )
  }
}
