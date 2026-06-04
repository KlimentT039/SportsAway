package com.diplomska.sportsaway.common.style.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.components.AppBar.HomeAppBar
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor

object AppBar {

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun HomeAppBar(
    title: String?,
    @DrawableRes icon: Int? = null,
    containerColor: Color = mainColor,
    contentColor: Color = topBarTextColor
  ) {
    CenterAlignedTopAppBar(
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          if (icon != null) {
            Icon(
              painter = painterResource(icon),
              contentDescription = null,
              modifier = Modifier.size(32.dp),
              tint = contentColor
            )
            Spacer(modifier = Modifier.width(8.dp))
          }
          Text(text = title.orEmpty())
        }
      },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = containerColor,
        titleContentColor = contentColor
      )
    )
  }


  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun CustomTopAppBar(
    title: String,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit,
    actionIcons: @Composable (RowScope.() -> Unit)? = null,
    containerColor: Color = mainColor,
    contentColor: Color = topBarTextColor
  ) {
    CenterAlignedTopAppBar(
      title = {
        Text(
          text = title,
          textAlign = TextAlign.Center,
          color = contentColor,
          style = MaterialTheme.typography.titleLarge
        )
      },
      navigationIcon = {
        if (showBackButton) {
          IconButton(onClick = onBackClick) {
            Icon(
              imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = stringResource(R.string.generic_back),
              tint = topBarTextColor
            )
          }
        }
      },
      actions = actionIcons ?: {},
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = containerColor,
        titleContentColor = contentColor
      )
    )
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun SearchAppBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    showBackButton: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
  ) {
    SearchBar(
      query = searchQuery,
      onQueryChange = onSearchQueryChange,
      onSearch = {},
      placeholder = {
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = stringResource(R.string.search_events_hint))
        }
      },
      leadingIcon = {
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (showBackButton) {
            IconButton(onClick = onBackClick) {
              Icon(
                imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.generic_back)
              )
            }
          }
          Spacer(modifier = Modifier.width(8.dp))
          Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.Search,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null
          )
        }
      },
      trailingIcon = {},
      content = content,
      active = true,
      onActiveChange = {},
      tonalElevation = 0.dp
    )
  }

  object Icons {
    @Composable
    fun Custom(
      image: ImageVector,
      onClick: () -> Unit,
      contentDescription: String? = null,
      contentColor: Color = topBarTextColor
    ) {
      IconButton(
        onClick = onClick,
      ) {
        Icon(
          imageVector = image,
          contentDescription = contentDescription,
          tint = contentColor
        )
      }
    }
  }
}


@Preview
@Composable
private fun PreviewCenterAlignedTopBar() {
  HomeAppBar(title = "SportsAway", icon = R.drawable.ic_logo)
}

@Preview
@Composable
private fun SearchBarPreview() {
  AppBar.SearchAppBar(
    searchQuery = "",
    onSearchQueryChange = {},
    content = {},
    showBackButton = true,
    onBackClick = {}
  )
}


@Preview
@Composable
private fun CustomTopAppBarPreview() {
  AppBar.CustomTopAppBar(
    title = stringResource(R.string.add_favourite_teams_title),
    onBackClick = { },
    actionIcons = { AppBar.Icons.Custom(image = Icons.Default.Check, onClick = {}) }
  )
}