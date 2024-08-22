package com.diplomska.sportsaway.common.shared.model

import android.os.Parcelable
import com.diplomska.sportsaway.common.shared.utils.parseDate
import com.diplomska.sportsaway.data.events_data.model.MatchResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
  val id: Int = 0,
  val date: String = "",
  val competition: Competition = Competition(),
  val homeTeam: Team = Team(),
  val awayTeam: Team = Team(),
  val trending: Boolean = false,
  val matchday: Int = 0,
  val generalTicket: Ticket = Ticket(),
  val vipTicket : Ticket = Ticket(),
) : Parcelable

fun MatchResponse.toMatch() = Match(
  id = id,
  date = parseDate(utcDate) ?: "",
  competition = competition.toCompetition(),
  homeTeam = homeTeam.toTeam(),
  awayTeam = awayTeam.toTeam(),
  trending = isTheMatchTrending(),
  matchday = matchday,
  generalTicket = Ticket().initRandomGeneralTickets(),
  vipTicket = Ticket().initRandomVipTickets()
)

private fun MatchResponse.isTheMatchTrending(): Boolean {
  val listOfTrendingTeams = listOf(
    "Manchester",
    "Chelsea",
    "Arsenal",
    "Liverpool",
    "Barcelona",
    "Real Madrid",
    "Milan",
    "Juventus",
    "Madrid"
  )

  return listOfTrendingTeams.any { team ->
    this.homeTeam.name.contains(team, ignoreCase = true) ||
      this.awayTeam.name.contains(team, ignoreCase = true)
  }
}

