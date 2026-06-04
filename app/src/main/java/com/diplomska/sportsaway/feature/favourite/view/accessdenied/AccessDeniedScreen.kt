package com.diplomska.sportsaway.feature.favourite.view.accessdenied

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.mainColor


@Composable
fun AccessDeniedScreen(message: String, buttonText: String?, onButtonClicked: () -> Unit) {
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
        text = message,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
      )
      Spacer(modifier = Modifier.height(32.dp))
      buttonText?.let {
        Button(
          shape = RoundedCornerShape(8.dp),
          onClick = onButtonClicked,
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = mainColor,
            contentColor = Color.White
          )
        ) {

          Text(text = buttonText, fontSize = 18.sp)
        }
      }
    }
  }
}

@Preview
@Composable
private fun PreviewAccessDeniedScreen() {
  AccessDeniedScreen(
    stringResource(id = R.string.access_denied_favourites),
    buttonText = stringResource(id = R.string.log_in),
    onButtonClicked = {}
  )
}
