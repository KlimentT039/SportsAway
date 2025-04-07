package com.diplomska.sportsaway.feature.favourite.view.news

import com.diplomska.sportsaway.R

enum class InfoTab {
  NEWS,
  SQUAD,
  INJURIES
}

fun InfoTab.getText(): Int {
  return when (this) {
    InfoTab.NEWS -> R.string.news
    InfoTab.INJURIES -> R.string.injuries
    InfoTab.SQUAD -> R.string.squad
  }
}