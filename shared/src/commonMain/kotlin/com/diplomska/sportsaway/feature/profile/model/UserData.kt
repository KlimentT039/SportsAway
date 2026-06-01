package com.diplomska.sportsaway.feature.profile.model

import com.diplomska.sportsaway.data.authentication_data.model.PersistedMatch

data class UserData(
  val username: String,
  val visitedMatches: List<PersistedMatch> = emptyList(),
  val upcomingMatches: List<PersistedMatch> = emptyList(),
  val upcomingTabIsSelected: Boolean = true
)
