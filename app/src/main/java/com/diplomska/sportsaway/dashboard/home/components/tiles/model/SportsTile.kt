package com.diplomska.sportsaway.dashboard.home.components.tiles.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.diplomska.sportsaway.R

sealed class SportsTile(
  val filterBySport: String,
  @DrawableRes val icon: Int,
  @StringRes val title: Int
) {
  data object Football : SportsTile("football", R.drawable.ic_soccer_ball, R.string.football)
  data object Basketball : SportsTile("basketball", R.drawable.ic_basketball_monocolor, R.string.basketball)
  data object Tennis : SportsTile("tennis", R.drawable.ic_tennis_racket, R.string.tennis)
  data object Formula : SportsTile("formula", R.drawable.ic_forumula, R.string.formula)
}

fun getSportsTiles() = listOf(
  SportsTile.Football,
  SportsTile.Basketball,
  SportsTile.Tennis,
  SportsTile.Formula
)