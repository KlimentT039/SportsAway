package com.diplomska.sportsaway.data.authentication_data.model

data class User(
  val mail: String = "",
  val username: String = "",
  val favouriteTeams: List<Int> = emptyList()
)
