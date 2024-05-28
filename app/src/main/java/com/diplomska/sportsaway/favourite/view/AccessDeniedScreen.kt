package com.diplomska.sportsaway.favourite.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.style.compose.theme.mainColor


@Composable
fun AccessDeniedScreen() {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = backgroundSurface
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(id = R.string.access_denied),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
      )
      Spacer(modifier = Modifier.height(32.dp))
      Button(
        shape = RoundedCornerShape(8.dp),
        onClick = {},
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        colors = ButtonDefaults.textButtonColors(
          backgroundColor = mainColor,
          contentColor = Color.White
        )
      ) {
        Text(text = stringResource(id = R.string.log_in), fontSize = 18.sp)
      }
    }
  }
}

@Preview
@Composable
private fun PreviewAccessDeniedScreen() {
  AccessDeniedScreen()
}
