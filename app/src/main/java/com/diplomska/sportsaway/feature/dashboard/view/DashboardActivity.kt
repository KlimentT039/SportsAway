package com.diplomska.sportsaway.feature.dashboard.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.feature.dashboard.home.components.tabs.model.DashboardTab
import com.diplomska.sportsaway.feature.dashboard.home.components.tabs.view.DashboardNavHost
import com.diplomska.sportsaway.feature.dashboard.home.components.tabs.view.DashboardTabNavigator

internal class DashboardActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Get the start destination from the intent
    val startDestination = intent.getStringExtra("startDestination") ?: DashboardTab.Home.route

    setContent {
      val navController = rememberNavController()

      Scaffold(
        topBar = { AppBar.HomeAppBar(title = stringResource(id = R.string.app_name)) },
        bottomBar = {
          DashboardTabNavigator(navController = navController)
        },
        containerColor = backgroundDefault,
        content = {
          Column(Modifier.padding(it)) {
            DashboardNavHost(navHostController = navController, startDestination = startDestination)
          }
        }
      )
    }
  }
}