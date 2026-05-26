package com.diplomska.sportsaway.feature.profile.model

import com.diplomska.sportsaway.common.shared.model.Match

data class UserData(
  val username: String,
  val visitedMatches: List<Match> = emptyList(),
  val upcomingMatches: List<Match> = emptyList(),
  val upcomingTabIsSelected: Boolean = true
)
