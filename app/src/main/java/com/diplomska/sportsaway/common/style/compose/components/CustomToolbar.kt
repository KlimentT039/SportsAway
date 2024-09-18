package com.diplomska.sportsaway.common.style.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextPrimary
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextSecondary
import com.diplomska.sportsaway.common.style.compose.typography

@Composable
fun CustomToolbar(
  title: String,
  description: String? = null,
  onBackPressed: () -> Unit,
  backButtonIcon: ImageVector
) {
  Surface(
    color = Color.Transparent,
    elevation = AppBarDefaults.TopAppBarElevation,
    shape = RoundedCornerShape(32.dp),
    modifier = Modifier.padding(10.dp)
  ) {
    TopAppBar(
      elevation = 0.dp,
      backgroundColor = backgroundSurface,
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
      ) {
        // Back button
        IconButton(
          onClick = { onBackPressed() },
          modifier = Modifier.padding(start = 4.dp)
        ) {
          Icon(
            backButtonIcon,
            contentDescription = "Back",
            tint = MaterialTheme.colors.onBackground
          )
        }
        // Custom title
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
          Text(
            text = title,
            style = typography.mRegular,
            color = MaterialTheme.colors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
              .padding(start = 32.dp)
          )
          description?.let {
            Text(
              text = description,
              style = typography.xsRegular,
              color = MaterialTheme.colors.onBackground,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier
                .padding(start = 32.dp)
            )
          }
        }
      }
    }
  }
}

@Preview
@Composable
fun Preview() {
  CustomToolbar(
    title = "Barcelona - Real Madrid",
    description = "Camp nou - 12.10.2021",
    onBackPressed = { /*TODO*/ },
    backButtonIcon = Icons.Filled.ArrowBack
  )
}