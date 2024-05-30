package com.diplomska.sportsaway.sports_data.repository

import com.diplomska.sportsaway.sports_data.model.EventsResponse

interface SportsEventsRepository {

  suspend fun fetchAllEvents(): List<EventsResponse>

  fun addTeamsToFireBase()

  fun addEventsToFirebase()
}