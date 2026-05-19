package com.diplomska.sportsaway.data.events_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchListResponse(
  @SerialName("matches") val matches: List<MatchResponse>
)

@Serializable
data class MatchResponse(
  @SerialName("id") val id: Int,
  @SerialName("utcDate") val utcDate: String,
  @SerialName("competition") val competition: CompetitionResponse,
  @SerialName("homeTeam") val homeTeam: TeamResponse,
  @SerialName("awayTeam") val awayTeam: TeamResponse,
  @SerialName("matchday") val matchday: Int,
  @SerialName("venue") val venue: String? = null
)
