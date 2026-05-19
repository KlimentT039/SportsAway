package com.diplomska.sportsaway.data.events_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
  @SerialName("title") val title: String,
  @SerialName("date") val date: String,
  @SerialName("summary") val summary: String,
  @SerialName("link") val link: String
)
