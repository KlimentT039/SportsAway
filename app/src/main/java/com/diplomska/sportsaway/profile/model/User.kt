package com.diplomska.sportsaway.profile.model

data class User(
  val mail: String = "",
  val username: String = "",
  val age: Int = 0,
  val favouriteTeam: String? = null
)
