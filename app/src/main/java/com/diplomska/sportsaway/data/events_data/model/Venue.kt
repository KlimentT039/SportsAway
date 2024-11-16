package com.diplomska.sportsaway.data.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class StadiumResponse(
  @JsonProperty("venues")
  val venues: List<Venue>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Venue(
  @JsonProperty("strFanart1")
  val strFanart1: String?,
  @JsonProperty("strFanart2")
  val strFanart2: String?,
  @JsonProperty("strFanart3")
  val strFanart3: String?,
  @JsonProperty("strFanart4z")
  val strFanart4: String?,
)