package com.diplomska.sportsaway.common.shared.model

import android.os.Parcelable
import com.diplomska.sportsaway.common.shared.utils.parseDate
import com.diplomska.sportsaway.common.shared.utils.parseDateToTime
import com.diplomska.sportsaway.data.events_data.model.MatchResponse
import kotlinx.parcelize.Parcelize

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

fun MatchResponse.toMatch() = Match(
  id = id,
  date = parseDate(utcDate) ?: "",
  time = parseDateToTime(utcDate) ?: "",
  competition = competition.toCompetition(),
  homeTeam = homeTeam.toTeam(),
  awayTeam = awayTeam.toTeam(),
  trending = isTheMatchTrending(),
  venue = venue,
  matchday = matchday,
  tickets = listOf(
    initRandomGeneralTickets(id, false),
    initRandomGeneralTickets(id, true),
    initRandomVipTickets(id)
  )
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

