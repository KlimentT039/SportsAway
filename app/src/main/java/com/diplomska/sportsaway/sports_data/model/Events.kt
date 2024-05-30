package com.diplomska.sportsaway.sports_data.model

import com.diplomska.sportsaway.events.model.Sport
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.Date

data class Events(@JsonProperty("events") val events: List<Event>)

data class Event(
  val homeTeam: String = "",
  val awayTeam: String = "",
  val competition: String = "",
  val sport: Sport = Sport.FOOTBALL,
  val date: Date = Date(),
  val id: String = "",
  val generalTickets: TicketResponse? = null,
  val vipTickets: TicketResponse? = null,
  val isItTrending: Boolean? = null
)