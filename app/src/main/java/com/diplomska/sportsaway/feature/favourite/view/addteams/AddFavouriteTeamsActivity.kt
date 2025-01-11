package com.diplomska.sportsaway.feature.favourite.view.addteams

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.diplomska.sportsaway.feature.dashboard.home.components.tabs.model.DashboardTab
import com.diplomska.sportsaway.feature.dashboard.view.DashboardActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFavouriteTeamsActivity : AppCompatActivity() {

  private val viewModel: AddFavouriteTeamsViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AddFavouriteTeamsScreen(onBackClick = { finish() })
    }
    observeEvents()
  }

  private fun observeEvents() = lifecycleScope.launch {
    viewModel.event.collect {
      if (it is FavouriteEvent.Done) {
        val intent = Intent(this@AddFavouriteTeamsActivity, DashboardActivity::class.java).apply {
          putExtra("startDestination", DashboardTab.Favourite.route)
        }
        startActivity(intent)
      }
    }
  }
}