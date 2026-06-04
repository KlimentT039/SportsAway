package com.diplomska.sportsaway.feature.authentication.register.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.diplomska.sportsaway.common.style.compose.theme.SportsAwayTheme
import com.diplomska.sportsaway.feature.dashboard.view.DashboardActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : AppCompatActivity() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, RegisterActivity::class.java)
    }
  }

  private val viewModel: RegisterViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SportsAwayTheme {
        CreateUserScreen()
      }
    }
    observeEvents()
  }

  private fun observeEvents() {
    lifecycleScope.launch {
      viewModel.event.collect { event ->
        when (event) {
          is RegisterEvent.NavigateToDashboard -> {
            finish()
          }
        }
      }
    }
  }
}