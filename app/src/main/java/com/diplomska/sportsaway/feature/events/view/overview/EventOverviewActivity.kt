package com.diplomska.sportsaway.feature.events.view.overview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

class EventOverviewActivity : AppCompatActivity() {

  companion object {
    const val EXTRA_COMPETITION = "extraCompetition"
    fun createIntent(context: Context, competitionId: Int?) =
      Intent(context, EventOverviewActivity::class.java).also {
        it.putExtra(EXTRA_COMPETITION, competitionId)
      }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val competitionId = intent.getIntExtra(EXTRA_COMPETITION, -1)
    setContent {
      EventsOverviewScreen(competitionId = competitionId, showBackButton = true)
    }
  }
}