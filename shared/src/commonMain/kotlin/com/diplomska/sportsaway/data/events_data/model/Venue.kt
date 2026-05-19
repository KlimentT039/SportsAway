package com.diplomska.sportsaway.data.events_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StadiumResponse(
  @SerialName("venues") val venues: List<Venue> = emptyList()
)

@Serializable
data class Venue(
  @SerialName("strFanart1") val strFanart1: String? = null,
  @SerialName("strFanart2") val strFanart2: String? = null,
  @SerialName("strFanart3") val strFanart3: String? = null,
  @SerialName("strFanart4z") val strFanart4: String? = null
)
