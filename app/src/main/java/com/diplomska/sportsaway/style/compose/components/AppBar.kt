package com.diplomska.sportsaway.style.compose.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.style.compose.components.AppBar.CenterAlignedTopBar
import com.diplomska.sportsaway.style.compose.theme.mainColor
import com.diplomska.sportsaway.style.compose.theme.topBarTextColor

object AppBar {

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun CenterAlignedTopBar(
    title: String?,
    containerColor: Color = mainColor,
    contentColor: Color = topBarTextColor
  ) {
    CenterAlignedTopAppBar(
      title = { Text(text = title.orEmpty()) },
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
    content: @Composable ColumnScope.() -> Unit
  ) {
    SearchBar(
      query = searchQuery,
      onQueryChange = onSearchQueryChange,
      onSearch = {},
      placeholder = {
        Text(text = "Search events")
      },
      leadingIcon = {
        Icon(
          imageVector = Icons.Default.Search,
          tint = MaterialTheme.colorScheme.onSurface,
          contentDescription = null
        )
      },
      trailingIcon = {},
      content = content,
      active = true,
      onActiveChange = {},
      tonalElevation = 0.dp
    )
  }
}


@Preview
@Composable
fun PreviewCenterAlignedTopBar() {
  CenterAlignedTopBar(title = "SportsAway")
}

@Preview
@Composable
fun SearchBarPreview() {
  AppBar.SearchAppBar(searchQuery = "", onSearchQueryChange = {}, content = {})
}