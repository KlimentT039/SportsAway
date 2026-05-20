package com.diplomska.sportsaway.common.shared.model

import com.diplomska.sportsaway.common.shared.utils.parseDate
import com.diplomska.sportsaway.common.shared.utils.parseDateToTime
import com.diplomska.sportsaway.data.events_data.model.MatchResponse

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
