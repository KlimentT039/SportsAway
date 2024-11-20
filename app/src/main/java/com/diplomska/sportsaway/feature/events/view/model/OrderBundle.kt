package com.diplomska.sportsaway.feature.events.view.model

import android.os.Parcelable
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Ticket
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderBundle(val match: Match, val selectedTicket: Ticket) : Parcelable
