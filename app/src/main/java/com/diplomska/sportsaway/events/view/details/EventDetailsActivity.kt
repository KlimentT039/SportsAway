package com.diplomska.sportsaway.events.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.diplomska.sportsaway.shared.model.Match
import com.diplomska.sportsaway.shared.utils.EXTRA_SELECTED_MATCH
import com.diplomska.sportsaway.shared.utils.getParcelableCompat
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailsActivity : AppCompatActivity() {

  companion object {
    fun createIntent(context: Context, selectedMatch: Match): Intent {
      return Intent(context, EventDetailsActivity::class.java).also {
        it.putExtra(EXTRA_SELECTED_MATCH, selectedMatch)
      }
    }
  }

  private val viewModel: EventDetailsViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val selectedMatch = intent.getParcelableCompat<Match>(EXTRA_SELECTED_MATCH)
    viewModel.initData(selectedMatch)
    setContent {
      EventDetailsScreen(
        onBackClick = { onBackPressedDispatcher.onBackPressed() },
        viewModel = viewModel
      )
    }
  }
}