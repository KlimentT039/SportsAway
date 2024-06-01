package com.diplomska.sportsaway.events_data.network

import com.diplomska.sportsaway.events_data.model.TeamResponse
import retrofit2.http.GET

interface SportsApi {

  @GET("/teams")
  suspend fun fetchTeams(): List<TeamResponse>

  suspend fun fetchTrendingMatches()

}