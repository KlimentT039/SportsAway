package com.diplomska.sportsaway.data.events_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamsResponse(
  @SerialName("teams") val teams: List<TeamResponse>
)
