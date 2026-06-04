package com.diplomska.sportsaway.feature.dashboard.home.components.tabs.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.diplomska.sportsaway.R

sealed class DashboardTab(val route: String, val icon: ImageVector, @StringRes val titleRes: Int) {
  data object Home : DashboardTab("home", Icons.Filled.Home, R.string.tab_home)
  data object Search : DashboardTab("search", Icons.Filled.Search, R.string.tab_search)
  data object Favourite : DashboardTab("favourite", Icons.Filled.Favorite, R.string.tab_favourite)
  data object Profile : DashboardTab("profile", Icons.Filled.Person, R.string.tab_profile)
}

fun getDashboardTabs() = listOf(
  DashboardTab.Home,
  DashboardTab.Search,
  DashboardTab.Favourite,
  DashboardTab.Profile
)
