package com.diplomska.sportsaway.data.authentication_data.model

import com.diplomska.sportsaway.common.shared.model.Match

data class User(
  val mail: String = "",
  val name: String = "",
  val favouriteTeams: List<Int> = emptyList(),
  val matches: List<Match> = emptyList()
)
