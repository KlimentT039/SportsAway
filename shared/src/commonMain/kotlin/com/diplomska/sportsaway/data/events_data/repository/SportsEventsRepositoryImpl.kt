package com.diplomska.sportsaway.data.events_data.repository

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.data.events_data.model.MatchResponse
import com.diplomska.sportsaway.data.events_data.model.StadiumResponse
import com.diplomska.sportsaway.data.events_data.model.TeamInfo
import com.diplomska.sportsaway.data.events_data.model.TeamResponse
import com.diplomska.sportsaway.data.events_data.model.TeamsResponse
import com.diplomska.sportsaway.data.events_data.model.listOfCompetitionIds
import com.diplomska.sportsaway.data.events_data.network.SportsApi
import com.diplomska.sportsaway.data.events_data.network.StadiumApi
import com.diplomska.sportsaway.data.events_data.provider.TeamInfoJsonProvider
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

class SportsEventsRepositoryImpl(
  private val sportsApi: SportsApi,
  private val stadiumApi: StadiumApi,
  private val teamInfoJsonProvider: TeamInfoJsonProvider
) : SportsEventsRepository, ErrorHandlingUseCase() {

  override suspend fun getMatches(competitionId: Int?): List<MatchResponse> {
    val competition = if (competitionId == -1) null else competitionId
    val competitions = competition?.toString() ?: listOfCompetitionIds.joinToString(",")
    return probeForUpcomingMatches { from, to ->
      sportsApi.getMatches(dateFrom = from, dateTo = to, competitions = competitions).matches
    }
  }

  override suspend fun getCompetitions() = sportsApi.getCompetitions().competitions

  override suspend fun getTeams(): TeamsResponse {
    return sportsApi.getTeams(limit = 100)
  }

  override suspend fun getMatchesByTeam(id: Int): List<MatchResponse> =
    probeForUpcomingMatches { from, to ->
      sportsApi.getMatchesByTeam(teamId = id, dateFrom = from, dateTo = to).matches
    }

  override suspend fun getMatchById(id: Int): MatchResponse {
    return sportsApi.getMatchDetails(id)
  }

  override suspend fun getStadiumPic(stadiumName: String): StadiumResponse {
    return stadiumApi.searchVenues(stadiumName)
  }

  override suspend fun getTeamById(id: Int): TeamResponse {
    return sportsApi.getTeamsById(id)
  }

  override suspend fun fetchLatestTeamInfo(id: Int): TeamInfo? {
    return teamInfoJsonProvider.teamsData[id.toString()]
  }

  /**
   * football-data.org caps match windows at 10 days. During the off-season the next 10 days are
   * empty, so probe forward in 10-day chunks until we find data — handles summer break, etc.
   */
  private suspend fun probeForUpcomingMatches(
    fetch: suspend (from: String, to: String) -> List<MatchResponse>
  ): List<MatchResponse> {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    repeat(PROBE_WINDOWS) { i ->
      val from = today.plus((1 + i * PROBE_WINDOW_DAYS).toLong(), DateTimeUnit.DAY)
      val to = from.plus((PROBE_WINDOW_DAYS - 1).toLong(), DateTimeUnit.DAY)
      val matches = fetch(from.toString(), to.toString())
      if (matches.isNotEmpty()) return matches
    }
    return emptyList()
  }

  companion object {
    private const val PROBE_WINDOW_DAYS = 10
    private const val PROBE_WINDOWS = 20
  }
}
