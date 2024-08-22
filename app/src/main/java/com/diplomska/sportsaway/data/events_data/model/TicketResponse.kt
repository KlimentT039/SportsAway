package com.diplomska.sportsaway.data.events_data.model

import com.google.firebase.firestore.PropertyName

data class TicketResponse(
  @get:PropertyName("availability") val availability: Int = 0,
  @get:PropertyName("price")val price: Int = 0
) {
  constructor() : this(availability = 0, price = 0)
}
