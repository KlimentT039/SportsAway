package com.diplomska.sportsaway.feature.authentication.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.diplomska.sportsaway.common.style.compose.theme.SportsAwayTheme
import com.diplomska.sportsaway.feature.authentication.register.view.RegisterActivity
import com.diplomska.sportsaway.feature.dashboard.home.components.tabs.model.DashboardTab
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, LoginActivity::class.java)
    }
  }

  private val viewModel by viewModel<LoginViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SportsAwayTheme {
        LoginScreen()
      }
    }
    observeEvent()
  }

  private fun observeEvent() = lifecycleScope.launch {
    viewModel.event.collect { event ->
      when (event) {
        is LoginEvents.SuccessfulLogin -> {
          finish()
        }

        is LoginEvents.NavigateToRegisterActivity -> {
          startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
          finish()
        }
      }
    }
  }
}