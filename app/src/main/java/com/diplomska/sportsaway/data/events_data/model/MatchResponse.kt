package com.diplomska.sportsaway.data.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchListResponse(@JsonProperty("matches") val matches: List<MatchResponse>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchResponse(
  @JsonProperty("id")
  val id: Int,
  @JsonProperty("utcDate")
  val utcDate: String,
  @JsonProperty("competition")
  val competition: CompetitionResponse,
  @JsonProperty("homeTeam")
  val homeTeam: TeamResponse,
  @JsonProperty("awayTeam")
  val awayTeam: TeamResponse,
  @JsonProperty("matchday")
  val matchday: Int,
  @JsonProperty("venue")
  val venue: String?
)
