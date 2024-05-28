package com.diplomska.sportsaway.events.domain

import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.events.model.Match
import com.diplomska.sportsaway.sports_data.model.EventsResponse
import com.diplomska.sportsaway.sports_data.model.TicketResponse
import com.diplomska.sportsaway.events.model.Ticket
import com.diplomska.sportsaway.events.model.mapToIconRes
import com.diplomska.sportsaway.shared.utils.parseDate

class MapEventsResponseToMatch {
  operator fun invoke(eventsResponse: EventsResponse): Match {

    return Match(
      homeTeam = eventsResponse.homeTeam,
      awayTeam = eventsResponse.awayTeam,
      competition = eventsResponse.competition,
      sport = eventsResponse.sport,
      date = parseDate(eventsResponse.date.toString()) ?: "",
      generalTickets = eventsResponse.generalTickets?.mapTicketResponseToTicket(true) ?: Ticket(),
      vipTickets = eventsResponse.vipTickets?.mapTicketResponseToTicket(false) ?: Ticket(),
      iconRes = eventsResponse.sport.mapToIconRes(),
      isItTrending = eventsResponse.isItTrending ?: false
    )
  }
}

private fun TicketResponse.mapTicketResponseToTicket(isItGeneralTicket: Boolean): Ticket {
  val title = if (isItGeneralTicket) R.string.general_ticket else R.string.vip_ticket
  return Ticket(title = title, remainingTickets = this.remainingTickets, price = this.price)
}