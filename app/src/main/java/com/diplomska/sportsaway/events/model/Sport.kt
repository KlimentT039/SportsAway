package com.diplomska.sportsaway.events.model

import com.diplomska.sportsaway.R

enum class Sport {
  FOOTBALL,
  BASKETBALL,
  HOCKEY,
  FORMULA,
  TENNIS,
  BASEBALL
}

fun Sport.mapToIconRes(): Int {
  return when (this) {
    Sport.FOOTBALL -> R.drawable.ic_soccer_ball
    Sport.BASKETBALL -> R.drawable.ic_basketball_monocolor
    Sport.FORMULA -> R.drawable.ic_forumula
    Sport.TENNIS -> R.drawable.ic_tennis_racket
    else -> R.drawable.ic_soccer_ball
  }
}