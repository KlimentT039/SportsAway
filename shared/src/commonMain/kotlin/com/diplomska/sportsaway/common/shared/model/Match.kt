package com.diplomska.sportsaway.common.shared.model

import com.diplomska.sportsaway.common.shared.parcelize.Parcelable
import com.diplomska.sportsaway.common.shared.parcelize.Parcelize
import com.diplomska.sportsaway.common.shared.utils.parseDate
import com.diplomska.sportsaway.common.shared.utils.parseDateToTime
import com.diplomska.sportsaway.data.events_data.model.MatchResponse
import kotlin.random.Random

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

private const val WORLD_CUP_COMPETITION_ID = 2000

private fun MatchResponse.isTheMatchTrending(): Boolean {
  // World Cup matches are randomly flagged trending (~50%) so the home feed has data
  // during the international window even when none of the marquee teams are playing.
  // Random per (matchId) so the flag is stable across re-fetches within a session.
  if (competition.id == WORLD_CUP_COMPETITION_ID) {
    return Random(id).nextBoolean()
  }

  val listOfTrendingTeams = listOf(
    // clubs
    "Manchester", "Chelsea", "Arsenal", "Liverpool",
    "Barcelona", "Real Madrid", "Madrid",
    "Milan", "Juventus", "Bayern", "Paris",
    // national teams
    "Brazil", "Argentina", "France", "Germany", "Spain",
    "England", "Portugal", "Netherlands", "Italy", "Belgium"
  )

  return listOfTrendingTeams.any { team ->
    this.homeTeam.name.contains(team, ignoreCase = true) ||
      this.awayTeam.name.contains(team, ignoreCase = true)
  }
}
