package com.diplomska.sportsaway.data.authentication_data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
  val mail: String = "",
  val name: String = "",
  val favouriteTeams: List<Int> = emptyList(),
  val matches: List<PersistedMatch> = emptyList()
)
