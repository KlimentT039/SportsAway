package com.diplomska.sportsaway.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Competition(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("code") val code: String,
  @JsonProperty("type") val type: String,
  @JsonProperty("emblem") val emblem: String?
)