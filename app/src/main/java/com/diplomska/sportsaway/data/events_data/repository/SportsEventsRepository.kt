package com.diplomska.sportsaway.data.events_data.repository

import com.diplomska.sportsaway.data.events_data.model.CompetitionResponse
import com.diplomska.sportsaway.data.events_data.model.EventsResponse
import com.diplomska.sportsaway.data.events_data.model.MatchResponse
import com.diplomska.sportsaway.data.events_data.model.StadiumResponse
import com.diplomska.sportsaway.data.events_data.model.TeamResponse
import com.diplomska.sportsaway.data.events_data.model.TeamsResponse
import com.diplomska.sportsaway.data.events_data.model.TeamInfo

interface SportsEventsRepository {

  suspend fun getMatches(competitionId: Int? = null): List<MatchResponse>

  suspend fun getCompetitions(): List<CompetitionResponse>

  suspend fun getTeams(): TeamsResponse

  suspend fun getMatchesByTeam(id: Int): List<MatchResponse>

  suspend fun getMatchById(id: Int): MatchResponse

  suspend fun getStadiumPic(stadiumName: String): StadiumResponse

  suspend fun getTeamById(id: Int): TeamResponse

  suspend fun fetchLatestTeamInfo(id: Int): TeamInfo?

}