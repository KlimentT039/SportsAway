package com.diplomska.sportsaway.common.shared.model

import com.diplomska.sportsaway.common.shared.parcelize.Parcelable
import com.diplomska.sportsaway.common.shared.parcelize.Parcelize

@Parcelize
data class Match(
  val id: Int = 0,
  val date: String = "",
  val time: String = "",
  val competition: Competition = Competition(),
  val homeTeam: Team = Team(),
  val awayTeam: Team = Team(),
  val trending: Boolean = false,
  val matchday: Int = 0,
  val tickets: List<Ticket> = emptyList(),
  val venue: String? = null,
  val venueImage: String? = null
) : Parcelable

fun Match.getMatchTitle() = "${homeTeam.name} vs ${awayTeam.name}"
fun Match.getMatchDescription() = "$date, $time at $venue".takeIf { venue != null }
