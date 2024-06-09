package com.diplomska.sportsaway.shared.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
  @StringRes val title: Int = 0,
  val remainingTickets: Int = 0,
  val price: Int = 0
): Parcelable
