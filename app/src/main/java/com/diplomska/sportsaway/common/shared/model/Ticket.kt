package com.diplomska.sportsaway.common.shared.model

import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.feature.events.view.model.TicketFilter
import kotlin.random.Random

fun initRandomGeneralTickets(matchId: Int, isItShortSide: Boolean) = Ticket(
  title = if (isItShortSide) R.string.shortside_ticket else R.string.longside_ticket,
  remainingTickets = Random.nextInt(from = 10, until = 20),
  section = Random.nextInt(100, 120).toString(),
  row = Random.nextInt(0, 19),
  price = Random.nextInt(30, 60),
  ticketType = TicketFilter.GENERAL,
  matchId = matchId
)


fun initRandomVipTickets(matchId: Int) = Ticket(
  title = R.string.vip_ticket,
  remainingTickets = Random.nextInt(from = 5, until = 15),
  price = Random.nextInt(80, 100),
  section = "VIP",
  row = null,
  ticketType = TicketFilter.VIP,
  matchId = matchId
)
