package com.diplomska.sportsaway.style.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.diplomska.sportsaway.style.compose.theme.backgroundSurface
import com.diplomska.style.compose.typography

@Composable
fun ImageTitleSubtitle(
  title: String,
  modifier: Modifier = Modifier,
  @DrawableRes drawableStart: Int? = null,
  drawableEnd: ImageVector? = null,
  subtitle: String? = null,
  onClick: (() -> Unit)? = null,
) {
  Row(
    modifier = modifier
      .background(backgroundSurface)
      .padding(16.dp)
      .let { if (onClick != null) it.clickable(onClick = onClick) else it }
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    drawableStart?.let {
      Image(
        painter = painterResource(id = drawableStart),
        contentDescription = "Sports Logo",
      )
    }

    Column(modifier = modifier.padding(start = 8.dp)) {
      Text(
        text = title,
        modifier = modifier.padding(bottom = 4.dp),
        style = typography.sRegularPrimary
      )

      subtitle?.let {
        Text(
          text = subtitle,
          style = typography.sRegularSecundary,
        )
      }
    }

    Spacer(modifier = Modifier.weight(1f))

    drawableEnd?.let {
      Image(
        imageVector = drawableEnd,
        contentDescription = "Sports Logo",
      )
    }
  }
}

@Composable
fun TileWithIconAndText(@DrawableRes iconDrawable: Int, text: String, color: Color = backgroundSurface) {
  Surface(
    modifier = Modifier
      .padding(8.dp)
      .size(100.dp), // Adjust the size as needed
    color = color
  ) {
    Column(
      modifier = Modifier
        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Icon(
        painter = painterResource(id = iconDrawable),
        contentDescription = null, // Decorative element
      )
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = text,
        maxLines = 1,
        softWrap = false
      )
    }
  }
}

@Composable
fun ListDivider(
  modifier: Modifier = Modifier,
) {
  Divider(
    thickness = 1.dp,
    startIndent = 16.dp,
    modifier = modifier.background(backgroundSurface)
  )
}

@Composable
private fun TileDemo() {
  Column {
    TileWithIconAndText(
      iconDrawable = R.drawable.ic_soccer_ball,
      text = "Football"
    )
  }
}

@Preview
@Composable
fun TilePreview() {
  TileDemo()
}

@Preview
@Composable
fun ImageTitleSubtitlePreview() {
  ImageTitleSubtitle(
    title = "Barcelona",
    subtitle = "22.03.2024 - Camp nou",
    drawableStart = R.drawable.ic_soccer_ball,
    drawableEnd = Icons.Filled.ArrowForward
  )
}
