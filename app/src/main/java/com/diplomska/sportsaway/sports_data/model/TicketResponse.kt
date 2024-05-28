package com.diplomska.sportsaway.sports_data.model

import com.google.firebase.firestore.PropertyName

data class TicketResponse(
  @get:PropertyName("remainingTickets") val remainingTickets: Int = 0,
  @get:PropertyName("price")val price: Int = 0
) {
  constructor() : this(remainingTickets = 0, price = 0)
}
