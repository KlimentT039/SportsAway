package com.diplomska.sportsaway.common.startup.splashscreen.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.diplomska.sportsaway.common.style.compose.theme.SportsAwayTheme
import com.diplomska.sportsaway.feature.dashboard.view.DashboardActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SportsAwayTheme {
        SplashScreen()
      }
    }
    setupDelayAndNavigate()
  }

  private fun setupDelayAndNavigate() {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed(
      {
        navigateToDashboard()
        finish()
      },
      3000
    )
  }

  private fun navigateToDashboard() {
    val intent = Intent(this, DashboardActivity::class.java)
    startActivity(intent)
  }
}