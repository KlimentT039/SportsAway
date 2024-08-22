package com.diplomska.sportsaway.data.events_data.network

import com.diplomska.sportsaway.data.events_data.model.CompetitionListResponse
import com.diplomska.sportsaway.data.events_data.model.MatchListResponse
import com.diplomska.sportsaway.data.events_data.model.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsApi {

  @GET("/teams")
  suspend fun fetchTeams(): List<TeamResponse>

  @GET("v4/matches")
  suspend fun getMatches(
    @Query("dateFrom") dateFrom: String,
    @Query("dateTo") dateTo: String,
    @Query("competitions") competitions: String,
  ): MatchListResponse

  @GET("v4/competitions")
  suspend fun getCompetitions(): CompetitionListResponse

}