package com.diplomska.sportsaway.data.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


data class TeamsResponse(
  @JsonProperty("teams")
  val teams: List<TeamResponse>
)
