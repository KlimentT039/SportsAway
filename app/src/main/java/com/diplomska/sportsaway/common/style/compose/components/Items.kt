package com.diplomska.sportsaway.common.style.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.typography


@Composable
fun TileWithIconAndText(
  imageRes: String?,
  text: String,
  color: Color = backgroundSurface,
  tileWidth: Dp = 120.dp,
  tileHeight: Dp = 120.dp
) {
  Surface(
    modifier = Modifier
      .padding(8.dp)
      .width(tileWidth)
      .height(tileHeight),
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
        if (!imageRes.isNullOrEmpty()) {
          ImageWithUrl(imageRes, 40)
        } else {
          Image(
            painter = painterResource(id = R.drawable.ic_generic_club),
            contentDescription = "Generic club picture",
            modifier = Modifier.size(40.dp)  // Set a fixed size for the image
          )
        }
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
      .padding(8.dp)
      .clickable(onClick = onClick ?: {})
  ) {
    Column(
      modifier = Modifier
        .background(color = backgroundSurface, shape = RoundedCornerShape(8.dp))
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = match.competition.name,
          style = typography.mLarge
        )
        Text(
          text = match.matchday.toString(),
          style = typography.mRegular
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          if (match.homeTeam.crest != null) {
            ImageWithUrl(match.homeTeam.crest, 50)
          } else {
            Image(
              painter = painterResource(id = R.drawable.ic_generic_club),
              contentDescription = "Generic club"
            )
          }

          Text(
            text = match.homeTeam.shortName,
            style = typography.mRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        Text(
          text = "vs",
          style = typography.mLarge,
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Column(
          modifier = Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          if (match.awayTeam.crest != null) {
            ImageWithUrl(match.awayTeam.crest, 50)
          } else {
            Image(
              painter = painterResource(id = R.drawable.ic_generic_club),
              contentDescription = "Generic club"
            )
          }
          Text(
            text = match.awayTeam.shortName,
            style = typography.mRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = match.homeTeam.venue,
        style = typography.sRegularPrimary
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
      imageRes = null,
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
private fun MatchCardPreview() {
  MatchCard(match = Match())
}

