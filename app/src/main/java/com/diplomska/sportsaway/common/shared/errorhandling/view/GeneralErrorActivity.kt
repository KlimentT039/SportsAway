package com.diplomska.sportsaway.common.shared.errorhandling.view

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.diplomska.sportsaway.common.shared.errorhandling.model.ErrorBundle

class GeneralErrorActivity: AppCompatActivity() {

  companion object {
    const val EXTRA_ERROR_BUNDLE = "errorBundle"

    fun createIntent(
      context: Context,
      @StringRes title: Int,
      @StringRes description: Int,
      onClick: () -> Unit
    ) = Intent(context, GeneralErrorActivity::class.java).also { intent ->
      val extraBundle = ErrorBundle(title, description, onClick)
      intent.putExtra(EXTRA_ERROR_BUNDLE, extraBundle)
    }
  }


}