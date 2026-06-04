package com.diplomska.sportsaway.common.style.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.utils.GetImage
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextPrimary
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextSecondary
import com.diplomska.sportsaway.common.style.compose.typography
import com.diplomska.sportsaway.data.authentication_data.model.PersistedMatch

private val CardShape = RoundedCornerShape(12.dp)
private val CardOuterPadding = PaddingDefaults(horizontal = 12.dp, vertical = 6.dp)
private val CardElevation = 2.dp

private data class PaddingDefaults(val horizontal: Dp, val vertical: Dp)

object MatchCard {

  @Composable
  fun Featured(
    match: Match,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
  ) {
    Card(
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = CardOuterPadding.horizontal, vertical = CardOuterPadding.vertical)
        .clickable(onClick = onClick),
      shape = CardShape,
      elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
      colors = CardDefaults.cardColors(containerColor = backgroundSurface)
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = match.competition.name,
            style = typography.mLarge.copy(fontWeight = FontWeight.Bold),
            color = typographyTextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = match.matchday.toString(),
            style = typography.mRegular.copy(fontWeight = FontWeight.SemiBold),
            color = typographyTextSecondary
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceEvenly,
          modifier = Modifier.fillMaxWidth()
        ) {
          TeamCrest(crest = match.homeTeam.crest, name = match.homeTeam.shortName)
          Text(
            text = "vs",
            style = typography.mLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = typographyTextSecondary
          )
          TeamCrest(crest = match.awayTeam.crest, name = match.awayTeam.shortName)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = match.homeTeam.venue,
          style = typography.sRegularPrimary.copy(color = typographyTextSecondary),
          textAlign = TextAlign.Center
        )
      }
    }
  }

  @Composable
  fun Compact(
    match: Match,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
  ) {
    Card(
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = CardOuterPadding.horizontal, vertical = CardOuterPadding.vertical)
        .clickable(onClick = onClick),
      shape = CardShape,
      elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
      colors = CardDefaults.cardColors(containerColor = backgroundSurface)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        TeamRow(crest = match.homeTeam.crest, name = match.homeTeam.name)
        Spacer(modifier = Modifier.height(8.dp))
        TeamRow(crest = match.awayTeam.crest, name = match.awayTeam.name)
      }
    }
  }

  @Composable
  fun WithVenue(
    match: PersistedMatch,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
  ) {
    Card(
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = CardOuterPadding.horizontal, vertical = CardOuterPadding.vertical)
        .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
      shape = CardShape,
      elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
      colors = CardDefaults.cardColors(containerColor = backgroundSurface)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        VenueImage(url = match.venueImage)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
          Text(
            text = "${match.homeTeamName} vs ${match.awayTeamName}",
            style = typography.sRegularPrimary.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = typographyTextPrimary
          )
          val dateLine = listOfNotNull(match.date.takeIf { it.isNotBlank() }, match.time.takeIf { it.isNotBlank() })
            .joinToString(", ")
          if (dateLine.isNotEmpty()) {
            Text(
              text = dateLine,
              style = typography.xsRegular,
              color = typographyTextSecondary
            )
          }
          match.venue?.takeIf { it.isNotBlank() }?.let {
            Text(
              text = it,
              style = typography.xsRegular,
              color = typographyTextSecondary
            )
          }
        }
      }
    }
  }
}

@Composable
private fun TeamCrest(crest: String?, name: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
private fun TeamRow(crest: String?, name: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    GetImage(imageRes = crest, pictureSize = 24)
    Spacer(modifier = Modifier.width(12.dp))
    Text(
      text = name,
      style = typography.sRegularPrimary,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
  }
}

@Composable
private fun VenueImage(url: String?) {
  if (!url.isNullOrEmpty()) {
    AsyncImage(
      model = url,
      contentDescription = null,
      modifier = Modifier
        .size(60.dp)
        .clip(RoundedCornerShape(8.dp)),
      contentScale = ContentScale.Crop
    )
  } else {
    Image(
      painter = painterResource(id = R.drawable.stadium_pic),
      contentDescription = null,
      modifier = Modifier
        .size(60.dp)
        .clip(RoundedCornerShape(8.dp)),
      contentScale = ContentScale.Crop
    )
  }
}

@Preview
@Composable
private fun FeaturedPreview() {
  MatchCard.Featured(match = Match())
}

@Preview
@Composable
private fun CompactPreview() {
  MatchCard.Compact(match = Match(), onClick = {})
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun WithVenuePreview() {
  MatchCard.WithVenue(
    match = PersistedMatch(
      homeTeamName = "Team A",
      awayTeamName = "Team B",
      date = "10 Dec 2024",
      time = "6:30 PM",
      venue = "Stadium 1",
    )
  )
}
