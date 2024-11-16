package com.diplomska.sportsaway.data.events_data.network

import com.diplomska.sportsaway.data.events_data.model.StadiumResponse
import com.diplomska.sportsaway.data.events_data.model.Venue
import retrofit2.http.GET
import retrofit2.http.Query

interface StadiumApi {
  @GET("searchvenues.php")
  suspend fun searchVenues(@Query("t") venueName: String): StadiumResponse
}