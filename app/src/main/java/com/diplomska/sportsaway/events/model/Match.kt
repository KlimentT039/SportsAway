package com.diplomska.sportsaway.events.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
  @DrawableRes val iconRes: Int = 0,
  val homeTeam: String = "",
  val awayTeam: String = "",
  val competition: String = "",
  val sport: Sport = Sport.FOOTBALL,
  val date: String = "",
  val isItTrending: Boolean = false,
  val generalTickets: Ticket = Ticket(),
  val vipTickets: Ticket = Ticket()
) : Parcelable
