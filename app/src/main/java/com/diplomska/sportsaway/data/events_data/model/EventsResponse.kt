package com.diplomska.sportsaway.data.events_data.model

import com.google.firebase.firestore.PropertyName
import java.util.*

data class EventsResponse(
  @get:PropertyName("homeTeam")
  val homeTeam: String = "",

  @get:PropertyName("awayTeam")
  val awayTeam: String = "",

  @get:PropertyName("competition")
  val competition: String = "",

//  @get:PropertyName("sport")
//  val sport: Sport = Sport.FOOTBALL,

  @get:PropertyName("date")
  val date: Date = Date(),

  @get:PropertyName("id")
  val id: String = "",

  @get:PropertyName("generalTickets")
  val generalTickets: TicketResponse? = null,

  @get:PropertyName("vipTickets")
  val vipTickets: TicketResponse? = null,

  @get:PropertyName("trending")
  val trending: Boolean = false
) {
  constructor() : this(
    id = "",
    homeTeam = "",
    awayTeam = "",
    competition = "",
//    sport = Sport.FOOTBALL,
    date = Date(),
    trending = false,
    vipTickets = TicketResponse(),
    generalTickets = TicketResponse()
  )
}
