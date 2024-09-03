package com.diplomska.sportsaway.feature.authentication.register.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, RegisterActivity::class.java)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CreateUserScreen()
    }
  }
}