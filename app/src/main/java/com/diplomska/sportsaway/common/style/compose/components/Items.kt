package com.diplomska.sportsaway.common.style.compose.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.utils.GetImage
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.sectionColor
import com.diplomska.sportsaway.common.style.compose.typography


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
fun ListDivider(
  modifier: Modifier = Modifier,
) {
  HorizontalDivider(
    thickness = 1.dp,
    modifier = modifier
      .background(backgroundSurface)
      .padding(start = 16.dp)
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
    Surface(color = sectionColor) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .padding(horizontal = 8.dp),
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
                imageVector = Icons.Outlined.Info,
                contentDescription = stringResource(R.string.generic_info),
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

@Preview
@Composable
private fun TilePreview() {
  TileWithIconAndText(imageRes = null, text = "Football", onClick = {})
}