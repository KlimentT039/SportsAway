package com.diplomska.sportsaway.feature.favourite.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.feature.favourite.view.addteams.AddFavouriteTeamsActivity

@Composable
fun NoTeamScreen() {
  val context = LocalContext.current
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundSurface),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = stringResource(id = R.string.add_first_team_title),
      style = MaterialTheme.typography.h4
    )
    Text(
      text = stringResource(id = R.string.add_first_team_description),
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(16.dp)
    )
    Button(
      onClick = { context.startActivity(Intent(context, AddFavouriteTeamsActivity::class.java)) },
      modifier = Modifier.padding(16.dp),
      colors = ButtonDefaults.textButtonColors(
        backgroundColor = mainColor,
        contentColor = Color.White
      )
    ) {
      Text(text = stringResource(id = R.string.search_team_button))
    }
  }
}

@Preview
@Composable
private fun PreviewNoTeamScreen() {
  NoTeamScreen()
}