package com.diplomska.sportsaway.feature.events.view.model

import androidx.annotation.StringRes
import com.diplomska.sportsaway.R

@StringRes
fun TicketTitle.toStringRes(): Int = when (this) {
  TicketTitle.SHORT_SIDE -> R.string.shortside_ticket
  TicketTitle.LONG_SIDE -> R.string.longside_ticket
  TicketTitle.VIP -> R.string.vip_ticket
}
