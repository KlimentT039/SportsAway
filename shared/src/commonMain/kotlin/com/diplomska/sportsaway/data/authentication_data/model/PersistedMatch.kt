package com.diplomska.sportsaway.data.authentication_data.model

import com.diplomska.sportsaway.common.shared.model.Match
import kotlinx.serialization.Serializable

@Serializable
data class PersistedMatch(
  val id: Int = 0,
  val date: String = "",
  val time: String = "",
  val homeTeamName: String = "",
  val awayTeamName: String = "",
  val venue: String? = null,
  val venueImage: String? = null
)

fun Match.toPersistedMatch() = PersistedMatch(
  id = id,
  date = date,
  time = time,
  homeTeamName = homeTeam.name,
  awayTeamName = awayTeam.name,
  venue = venue,
  venueImage = venueImage
)
