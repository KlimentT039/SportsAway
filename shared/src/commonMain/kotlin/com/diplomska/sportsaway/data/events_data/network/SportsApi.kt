package com.diplomska.sportsaway.data.events_data.network

import com.diplomska.sportsaway.data.events_data.model.CompetitionListResponse
import com.diplomska.sportsaway.data.events_data.model.MatchListResponse
import com.diplomska.sportsaway.data.events_data.model.MatchResponse
import com.diplomska.sportsaway.data.events_data.model.TeamResponse
import com.diplomska.sportsaway.data.events_data.model.TeamsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val FOOTBALL_BASE_URL = "https://api.football-data.org"

class SportsApi(private val client: HttpClient) {

  suspend fun getTeamsById(id: Int): TeamResponse =
    client.get("$FOOTBALL_BASE_URL/v4/teams/$id").body()

  suspend fun getMatches(
    dateFrom: String,
    dateTo: String,
    competitions: String,
  ): MatchListResponse =
    client.get("$FOOTBALL_BASE_URL/v4/matches") {
      parameter("dateFrom", dateFrom)
      parameter("dateTo", dateTo)
      parameter("competitions", competitions)
    }.body()

  suspend fun getCompetitions(): CompetitionListResponse =
    client.get("$FOOTBALL_BASE_URL/v4/competitions").body()

  suspend fun getTeams(limit: Int): TeamsResponse =
    client.get("$FOOTBALL_BASE_URL/v4/teams") {
      parameter("limit", limit)
    }.body()

  suspend fun getMatchesByTeam(
    teamId: Int,
    dateFrom: String,
    dateTo: String,
  ): MatchListResponse =
    client.get("$FOOTBALL_BASE_URL/v4/teams/$teamId/matches") {
      parameter("dateFrom", dateFrom)
      parameter("dateTo", dateTo)
    }.body()

  suspend fun getMatchDetails(id: Int): MatchResponse =
    client.get("$FOOTBALL_BASE_URL/v4/matches/$id").body()
}
