package com.diplomska.sportsaway.feature.events.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.utils.EXTRA_SELECTED_MATCH
import com.diplomska.sportsaway.common.shared.utils.getParcelableCompat
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailsActivity : AppCompatActivity() {

  companion object {
    fun createIntent(context: Context, selectedMatchId: Int): Intent {
      return Intent(context, EventDetailsActivity::class.java).also {
        it.putExtra(EXTRA_SELECTED_MATCH, selectedMatchId)
      }
    }
  }

  private val viewModel: EventDetailsViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val selectedMatchId = intent.getIntExtra(EXTRA_SELECTED_MATCH, 0)
    viewModel.initData(selectedMatchId)
    setContent {
      EventDetailsScreen(
        matchId = selectedMatchId,
        onBackClick = { onBackPressedDispatcher.onBackPressed() })
    }
  }
}