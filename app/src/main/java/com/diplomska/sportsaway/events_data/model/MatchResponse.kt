package com.diplomska.sportsaway.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.sql.Date

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchResponse(
  @JsonProperty("id")
  val id: Int,
  @JsonProperty("date")
  val utcDate: Date,
  @JsonProperty("competition")
  val competition: Competition,
  @JsonProperty("homeTeam")
  val homeTeam: TeamResponse,
  @JsonProperty("awayTeam")
  val awayTeam: TeamResponse
)
