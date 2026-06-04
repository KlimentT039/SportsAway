package com.diplomska.sportsaway.feature.events.view.order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.diplomska.sportsaway.common.shared.utils.getParcelableCompat
import com.diplomska.sportsaway.common.style.compose.theme.SportsAwayTheme
import com.diplomska.sportsaway.feature.events.view.model.OrderBundle
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderTicketsActivity : AppCompatActivity() {

  companion object {
    private val EXTRA_BUNDLE = "orderBundle"

    fun createIntent(context: Context, orderBundle: OrderBundle) =
      Intent(context, OrderTicketsActivity::class.java).apply {
        putExtra(EXTRA_BUNDLE, orderBundle)
      }
  }

  private val viewModel: OrderTicketsViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val orderBundle = intent.getParcelableCompat<OrderBundle>(EXTRA_BUNDLE) ?: return
    viewModel.initData(orderBundle)
    setContent {
      SportsAwayTheme {
        OrderTicketsScreen(
          viewModel = viewModel,
          onBackClick = { onBackPressedDispatcher.onBackPressed() }
        )
      }
    }
  }
}