package com.diplomska.sportsaway.data.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class News(
  @JsonProperty("title")
  val title: String,
  @JsonProperty("date")
  val date: String,
  @JsonProperty("summary")
  val summary: String,
  @JsonProperty("link")
  val link: String
)
