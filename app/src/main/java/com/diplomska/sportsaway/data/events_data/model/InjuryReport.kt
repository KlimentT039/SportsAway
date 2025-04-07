package com.diplomska.sportsaway.data.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class InjuryReport(
  @JsonProperty("player")
  val player: String,
  @JsonProperty("injury")
  val injury: String,
  @JsonProperty("expected_return")
  val expectedReturn: String,
  @JsonProperty("status")
  val status: String
)
