import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor
import com.diplomska.sportsaway.common.style.compose.typography

@Composable
fun ErrorScreen(
  title: String,
  onClick: () -> Unit,
  description: String? = null,
  @DrawableRes drawable: Int = R.drawable.ic_warning,
  buttonText: String = stringResource(R.string.try_again),
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(bottom = 48.dp)
      .background(backgroundSurface),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
        painter = painterResource(id = drawable),
        contentDescription = null
      )
      Spacer(modifier = Modifier.height(24.dp))
      Text(text = title, style = typography.mLarge)
      Spacer(modifier = Modifier.height(16.dp))
      if (description != null) {
        Text(text = description, style = typography.mRegular)
      }
    }

    Button(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = mainColor,
        contentColor = topBarTextColor
      ),
      onClick = onClick,
    ) {
      Text(text = buttonText)
    }
  }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
  ErrorScreen(
    title = "Something is wrong",
    description = "Fix it",
    drawable = R.drawable.ic_warning,
    onClick = {})
}