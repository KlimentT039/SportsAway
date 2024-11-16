package com.diplomska.sportsaway.data.events_data.network

import com.diplomska.sportsaway.data.events_data.model.CompetitionListResponse
import com.diplomska.sportsaway.data.events_data.model.MatchListResponse
import com.diplomska.sportsaway.data.events_data.model.MatchResponse
import com.diplomska.sportsaway.data.events_data.model.TeamResponse
import com.diplomska.sportsaway.data.events_data.model.TeamsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SportsApi {

  @GET("v4/teams/{id}")
  suspend fun getTeamsById(@Path("id") id: Int): TeamResponse

  @GET("v4/matches")
  suspend fun getMatches(
    @Query("dateFrom") dateFrom: String,
    @Query("dateTo") dateTo: String,
    @Query("competitions") competitions: String,
  ): MatchListResponse

  @GET("v4/competitions")
  suspend fun getCompetitions(): CompetitionListResponse

  @GET("v4/teams")
  suspend fun getTeams(@Query("limit") limit: Int): TeamsResponse

  @GET("v4/teams/{id}/matches")
  suspend fun getMatchesByTeam(
    @Path("id") teamId: Int,
    @Query("dateFrom") dateFrom: String,
    @Query("dateTo") dateTo: String,
  ): MatchListResponse

  @GET("v4/matches/{id}")
  suspend fun getMatchDetails(
    @Path("id") id: Int
  ): MatchResponse

}