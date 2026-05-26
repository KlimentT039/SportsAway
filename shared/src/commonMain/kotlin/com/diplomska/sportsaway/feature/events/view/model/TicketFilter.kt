package com.diplomska.sportsaway.feature.events.view.model

import kotlinx.serialization.Serializable

@Serializable
enum class TicketFilter(val displayName: String) {
  GENERAL("General"),
  VIP("VIP")
}
