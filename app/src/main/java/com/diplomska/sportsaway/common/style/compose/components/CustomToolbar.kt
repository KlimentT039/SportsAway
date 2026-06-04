package com.diplomska.sportsaway.common.style.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.typography

@Composable
fun CustomToolbar(
  title: String,
  description: String? = null,
  onBackPressed: () -> Unit,
  backButtonIcon: ImageVector
) {
  Surface(
    color = backgroundSurface,
    shadowElevation = 4.dp,
    shape = RoundedCornerShape(32.dp),
    modifier = Modifier.padding(10.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
    ) {
      IconButton(
        onClick = onBackPressed,
        modifier = Modifier.padding(start = 4.dp)
      ) {
        Icon(
          backButtonIcon,
          contentDescription = stringResource(R.string.generic_back),
          tint = MaterialTheme.colorScheme.onBackground
        )
      }
      Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
          text = title,
          style = typography.mRegular,
          color = MaterialTheme.colorScheme.onBackground,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.padding(start = 5.dp)
        )
        description?.let {
          Text(
            text = it,
            style = typography.xsRegular,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 5.dp)
          )
        }
      }
    }
  }
}

@Preview
@Composable
private fun CustomToolbarPreview() {
  CustomToolbar(
    title = "Barcelona - Real Madrid",
    description = "Camp nou - 12.10.2021",
    onBackPressed = {},
    backButtonIcon = Icons.AutoMirrored.Filled.ArrowBack
  )
}
