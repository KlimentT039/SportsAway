package com.diplomska.sportsaway.common.style.compose.components

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Competition
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.typography
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.shared.utils.GetImage
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.sectionColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextPrimary
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextSecondary


@Composable
fun TileWithIconAndText(
  imageRes: String?,
  text: String,
  color: Color = backgroundSurface,
  tileWidth: Dp = 120.dp,
  tileHeight: Dp = 120.dp,
  onClick: () -> Unit,
) {
  Surface(
    modifier = Modifier
      .padding(8.dp)
      .width(tileWidth)
      .height(tileHeight)
      .clickable { onClick() },
    color = color
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        GetImage(imageRes = imageRes)
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = text,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Ellipsis,  // Handle overflow gracefully
        modifier = Modifier.align(Alignment.CenterHorizontally)
      )
    }
  }
}

@Composable
fun MatchCard(
  match: Match,
  onClick: (() -> Unit)? = null,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 8.dp)
      .clickable(onClick = onClick ?: {}),
    shape = RoundedCornerShape(12.dp),
    elevation = 4.dp,
    backgroundColor = backgroundSurface
  ) {
    Column(
      modifier = Modifier
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = match.competition.name,
          style = typography.mLarge.copy(fontWeight = FontWeight.Bold),
          color = Color.Black
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
          text = match.matchday.toString(),
          style = typography.mRegular.copy(fontWeight = FontWeight.SemiBold),
          color = Color.Gray
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly, // Changed from weight to SpaceEvenly
        modifier = Modifier.fillMaxWidth()
      ) {
        TeamInfo(
          crest = match.homeTeam.crest,
          name = match.homeTeam.shortName
        )
        Text(
          text = "vs",
          style = typography.mLarge.copy(fontWeight = FontWeight.Bold),
          modifier = Modifier.padding(horizontal = 16.dp),
          color = Color.Gray
        )
        TeamInfo(
          crest = match.awayTeam.crest,
          name = match.awayTeam.shortName
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = match.homeTeam.venue,
        style = typography.sRegularPrimary.copy(color = Color.Gray),
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
fun TeamInfo(crest: String?, name: String) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    GetImage(imageRes = crest, pictureSize = 40)
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = name,
      style = typography.mRegular.copy(fontWeight = FontWeight.Medium),
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      textAlign = TextAlign.Center
    )
  }
}


@Composable
fun MatchItem(match: Match, onClick: (() -> Unit)) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .background(backgroundSurface)
      .clickable(onClick = { onClick() })
  ) {
    Row(
      Modifier
        .fillMaxWidth()
        .padding(5.dp)
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
        ) {
          GetImage(imageRes = match.homeTeam.crest, pictureSize = 20)
          Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = match.homeTeam.name,
            style = typography.xsRegular,
          )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
        ) {
          GetImage(imageRes = match.awayTeam.crest, pictureSize = 20)
          Text(
            text = match.awayTeam.name,
            style = typography.xsRegular,
            modifier = Modifier.padding(horizontal = 8.dp),
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
fun MatchSection(
  imageUrl: String?,
  name: String,
  isContentVisible: Boolean,
  showInfo: Boolean = false,
  onInfoClick: (() -> Unit)? = null,
  onVisibilityChange: (Boolean) -> Unit,
  content: @Composable () -> Unit,
) {
  Column {
    TopAppBar(
      backgroundColor = sectionColor,
      contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          GetImage(imageRes = imageUrl, pictureSize = 30)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = name,
            style = typography.mRegular,
            fontWeight = FontWeight.Bold
          )
          if (showInfo && onInfoClick != null) {
            IconButton(onClick = onInfoClick) {
              Icon(
                imageVector = androidx.compose.material.icons.Icons.Outlined.Info,
                contentDescription = "Info",
                tint = Color.Black
              )
            }
          }
        }
        TextButton(onClick = { onVisibilityChange(!isContentVisible) }) {
          Text(
            text = if (isContentVisible) stringResource(id = R.string.hide_section)
            else stringResource(id = R.string.show_section),
            style = typography.sRegularSecundary,
          )
        }
      }
    }

    if (isContentVisible) {
      content()
    }
  }
}

@Composable
fun MatchTile(match: Match) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(backgroundSurface)
      .padding(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Team Logos or Match Image
    if (!match.venueImage.isNullOrEmpty()) {
      AsyncImage(
        model = match.venueImage,
        contentDescription = "Match Image",
        modifier = Modifier
          .size(60.dp)
          .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.stadium_pic),
        contentDescription = "Match Image",
        modifier = Modifier
          .size(60.dp)
          .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
      )
    }

    Spacer(modifier = Modifier.width(16.dp))

    // Match Details
    Column {
      Text(
        text = "${match.homeTeam.name} vs ${match.awayTeam.name}",
        color = typographyTextPrimary,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
      )
      Text(
        text = "${match.date}, ${match.time}",
        color = typographyTextSecondary,
        fontSize = 14.sp
      )
      Text(
        text = match.venue ?: "",
        color = Color.Gray,
        fontSize = 12.sp
      )
    }
  }
}

@Composable
private fun TileDemo() {
  Column {
    TileWithIconAndText(
      imageRes = null,
      text = "Football",
      onClick = {}
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
private fun MatchCardPreview() {
  MatchCard(match = Match())
}

@Preview
@Composable
fun MatchItemPreview() {
  MatchItem(match = Match(), onClick = {})
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewGameTile() {
  MatchTile(
    match = Match(
      homeTeam = Team(name = "Team A"),
      awayTeam = Team(name = "Team B"),
      date = "10 Dec 2024",
      time = "6:30 PM",
      venue = "Stadium 1",
    )
  )
}