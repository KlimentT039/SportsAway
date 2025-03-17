package com.diplomska.sportsaway.data.events_data.model

data class InjuryReport(
  val player: String,
  val injury: String,
  val expectedReturn: String,
  val status: String
)
