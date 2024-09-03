package com.diplomska.sportsaway.feature.dashboard.home.components.tabs.view

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.feature.dashboard.home.components.tabs.model.DashboardTab
import com.diplomska.sportsaway.feature.dashboard.home.components.tabs.model.getDashboardTabs
import com.diplomska.sportsaway.feature.dashboard.home.view.HomeScreen
import com.diplomska.sportsaway.feature.events.view.overview.EventsOverviewScreen
import com.diplomska.sportsaway.feature.favourite.view.FavouriteScreen
import com.diplomska.sportsaway.feature.profile.ui.ProfileScreen

@Composable
fun DashboardTabNavigator(
  navController: NavHostController
) {
  var currentRoute by remember { mutableStateOf<String?>(DashboardTab.Home.route) }

  BottomNavigation(
    backgroundColor = backgroundSurface
  ) {
    getDashboardTabs().forEach { tab ->
      BottomNavigationItem(
        selected = currentRoute == tab.route,
        onClick = {
          if (currentRoute != tab.route) {
            navController.navigate(tab.route)
            currentRoute = tab.route
          }
        },
        icon = {
          Icon(
            imageVector = tab.icon,
            contentDescription = tab.title,
          )
        },
        selectedContentColor = mainColor,
        unselectedContentColor = Color.Gray
      )
    }
  }
}

@Composable
fun DashboardNavHost(navHostController: NavHostController) {
  NavHost(
    navController = navHostController,
    startDestination = DashboardTab.Home.route,
  ) {
    composable(DashboardTab.Home.route) {
      HomeScreen()
    }
    composable(DashboardTab.Search.route) {
      EventsOverviewScreen()
    }
    composable(DashboardTab.Favourite.route) {
      FavouriteScreen()
    }
    composable(DashboardTab.Profile.route) {
      ProfileScreen()
    }
  }
}