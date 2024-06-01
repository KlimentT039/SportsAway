package com.diplomska.sportsaway.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TeamResponse(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("shortName") val shortName: String,
  @JsonProperty("tla") val tla: String,
  @JsonProperty("crest") val crest: String,
  @JsonProperty("venue") val venue: String
)