package com.diplomska.sportsaway.data.events_data.network

import com.diplomska.sportsaway.data.events_data.model.StadiumResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val STADIUM_BASE_URL = "https://www.thesportsdb.com/api/v1/json/3"

class StadiumApi(private val client: HttpClient) {

  suspend fun searchVenues(venueName: String): StadiumResponse =
    client.get("$STADIUM_BASE_URL/searchvenues.php") {
      parameter("t", venueName)
    }.body()
}
