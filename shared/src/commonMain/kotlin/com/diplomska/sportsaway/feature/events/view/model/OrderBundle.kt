package com.diplomska.sportsaway.feature.events.view.model

import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.common.shared.parcelize.Parcelable
import com.diplomska.sportsaway.common.shared.parcelize.Parcelize

@Parcelize
data class OrderBundle(val match: Match, val selectedTicket: Ticket) : Parcelable
