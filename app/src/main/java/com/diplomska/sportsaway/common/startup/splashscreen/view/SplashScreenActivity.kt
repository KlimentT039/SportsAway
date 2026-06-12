package com.diplomska.sportsaway.common.startup.splashscreen.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.diplomska.sportsaway.common.style.compose.theme.SportsAwayTheme
import com.diplomska.sportsaway.feature.dashboard.view.DashboardActivity
import com.diplomska.sportsaway.feature.startup.StartupViewModel
import com.diplomska.sportsaway.feature.startup.StartupViewState
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {

  private val viewModel: StartupViewModel by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SportsAwayTheme {
        SplashScreen()
      }
    }
    viewModel.resolve()
    lifecycleScope.launch {
      viewModel.viewState.filterIsInstance<StartupViewState.Ready>().first()
      navigateToDashboard()
      finish()
    }
  }

  private fun navigateToDashboard() {
    startActivity(Intent(this, DashboardActivity::class.java))
  }
}
