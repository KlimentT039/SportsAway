package com.diplomska.sportsaway.common.shared.model

import com.diplomska.sportsaway.common.shared.parcelize.Parcelable
import com.diplomska.sportsaway.common.shared.parcelize.Parcelize
import com.diplomska.sportsaway.feature.events.view.model.TicketFilter

@Parcelize
data class Ticket(
  val title: Int = 0,
  val remainingTickets: Int = 0,
  val price: Int = 0,
  val section: String = "101",
  val row: Int? = null,
  val ticketType: TicketFilter = TicketFilter.GENERAL,
  val matchId: Int = -1
) : Parcelable
