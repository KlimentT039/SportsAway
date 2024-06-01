package com.diplomska.sportsaway.style.compose.components

import android.R.attr.maxLines
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.events.model.Match
import com.diplomska.sportsaway.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.style.compose.typography


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
      .height(45.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    drawableStart?.let {
      Image(
        painter = painterResource(id = it),
        contentDescription = null,
        modifier = Modifier.size(30.dp) // Adjust size as needed
      )
    }

    Column(
      modifier = Modifier
        .padding(start = 8.dp)
        .weight(1f),
      verticalArrangement = Arrangement.Center
    ) {
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

    drawableEnd?.let {
      Icon(
        imageVector = it,
        contentDescription = null,
        modifier = Modifier.size(24.dp) // Adjust size as needed
      )
    }
  }
}


@Composable
fun TileWithIconAndText(
  @DrawableRes iconDrawable: Int,
  text: String,
  color: Color = backgroundSurface
) {
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
fun MatchEvent(title: String, date: String, price: String, imageRes: Int, onClick: (() -> Unit)?) {
  Column(
    modifier = Modifier
      .width(200.dp)
      .background(backgroundSurface, RoundedCornerShape(8.dp))

  ) {
    Image(
      painter = painterResource(id = imageRes),
      contentDescription = null,
      modifier = Modifier
        .height(120.dp)
        .fillMaxWidth(),
      contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.height(8.dp))
    Column(modifier = Modifier.padding(8.dp)) {
      Text(
        text = title, style = typography.mRegular, maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
      Text(text = date, style = typography.sRegularPrimary)
      Text(text = price, style = typography.sRegularSecundary)
    }
  }
}

@Composable
fun EventItem(match: Match) {
  Box(
    modifier = Modifier
      .width(300.dp)
      .background(
        brush = Brush.linearGradient(
          colors = listOf(Color(0xFFE3F2FD), Color(0xFF90CAF9)),
          start = Offset(0f, 0f),
          end = Offset(1000f, 1000f)
        ),
        shape = RoundedCornerShape(16.dp)
      )
      .padding(8.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(Color(0xFF1976D2)),
        contentAlignment = Alignment.Center
      ) {
        BasicText(
          text = "1",
          style = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
            shadow = Shadow(color = Color.Gray, blurRadius = 4f)
          )
        )
      }
      Spacer(modifier = Modifier.width(8.dp))
      Column {
        BasicText(
          text = "${match.homeTeam} vs ${match.awayTeam}",
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          style = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
            shadow = Shadow(color = Color.Gray, blurRadius = 4f)
          )
        )
        BasicText(
          text = match.date,
          style = TextStyle(fontSize = 14.sp, color = Color.DarkGray)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {

          BasicText(
            text = match.homeTeam,
            style = TextStyle(fontSize = 14.sp, color = Color.DarkGray)
          )
        }
      }
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
    title = "Los Angeles Lakers - Golden State Warriors",
    subtitle = "22.03.2024 - Camp nou",
    drawableStart = R.drawable.ic_basketball_monocolor
  )
}

@Preview
@Composable
fun MatchEventPreview() {
  MatchEvent(
    title = "Barcelona - Real Madrid",
    date = "22.10.2023",
    price = "From 50$",
    imageRes = R.drawable.soccer_stadium,
    onClick = {}
  )
}

@Preview
@Composable
fun EventPreview() {
  EventItem(match = Match(homeTeam = "Barcelona", awayTeam = "Real Madrdi", date = "Feb 15"))
}
