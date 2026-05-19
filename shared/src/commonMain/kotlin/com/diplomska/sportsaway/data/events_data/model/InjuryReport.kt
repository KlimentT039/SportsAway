package com.diplomska.sportsaway.data.events_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InjuryReport(
  @SerialName("player") val player: String,
  @SerialName("injury") val injury: String,
  @SerialName("expected_return") val expectedReturn: String,
  @SerialName("status") val status: String
)
