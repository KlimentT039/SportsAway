//package com.diplomska.sportsaway.events.domain
//
//import com.diplomska.sportsaway.R
//import com.diplomska.sportsaway.events.model.Match
//import com.diplomska.sportsaway.events_data.model.EventsResponse
//import com.diplomska.sportsaway.events_data.model.TicketResponse
//import com.diplomska.sportsaway.shared.model.Ticket
//import com.diplomska.sportsaway.events.model.mapToIconRes
//import com.diplomska.sportsaway.shared.model.Match
//import com.diplomska.sportsaway.shared.utils.com.diplomska.sportsaway.common.shared.utils.parseDate
//
//class MapEventsResponseToMatch {
//  operator fun invoke(eventsResponse: EventsResponse): Match {
//
//    return Match(
//      homeTeam = eventsResponse.homeTeam,
//      awayTeam = eventsResponse.awayTeam,
//      competition = eventsResponse.competition,
//
//    )
//  }
//}
//
//private fun TicketResponse.mapTicketResponseToTicket(isItGeneralTicket: Boolean): Ticket {
//  val title = if (isItGeneralTicket) R.string.general_ticket else R.string.vip_ticket
//  return Ticket(title = title, remainingTickets = this.availability, price = this.price)
//}