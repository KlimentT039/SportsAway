package com.diplomska.sportsaway.feature.dashboard.home.components.tabs.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DashboardTab(val route: String, val icon: ImageVector, var title: String) {
  data object Home : DashboardTab(route = "home", icon = Icons.Filled.Home, "Home")
  data object Search : DashboardTab(route = "search", icon = Icons.Filled.Search, "Search")
  data object Favourite : DashboardTab(route = "favourite", icon = Icons.Filled.Favorite, "Favourite")
  data object Profile : DashboardTab(route = "profile", icon = Icons.Filled.Person, "Profile")
}

fun getDashboardTabs() = listOf(
  DashboardTab.Home,
  DashboardTab.Search,
  DashboardTab.Favourite,
  DashboardTab.Profile
)
