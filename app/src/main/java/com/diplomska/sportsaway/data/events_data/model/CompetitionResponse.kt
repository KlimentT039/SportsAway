package com.diplomska.sportsaway.data.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

data class CompetitionListResponse(
  @JsonProperty("competitions")
  val competitions: List<CompetitionResponse>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CompetitionResponse(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("code") val code: String,
  @JsonProperty("type") val type: String,
  @JsonProperty("emblem") val emblem: String?
)

val listOfCompetitionIds = listOf(2001, 2021, 2014, 2019, 2012, 2015)