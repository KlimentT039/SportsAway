package com.diplomska.sportsaway.feature.favourite.view.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.diplomska.sportsaway.common.style.compose.theme.SportsAwayTheme

class LatestNewsActivity : AppCompatActivity() {

  companion object {

    private const val EXTRA_TEAM_ID = "extra.team.id"

    fun createIntent(context: Context, teamId: Int) =
      Intent(context, LatestNewsActivity::class.java).apply {
        putExtra(EXTRA_TEAM_ID, teamId)
      }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val teamId = intent.getIntExtra(EXTRA_TEAM_ID, -1)
    setContent {
      SportsAwayTheme {
        LatestNewsScreen(teamId = teamId, onBackClicked = onBackPressedDispatcher::onBackPressed)
      }
    }
  }
}