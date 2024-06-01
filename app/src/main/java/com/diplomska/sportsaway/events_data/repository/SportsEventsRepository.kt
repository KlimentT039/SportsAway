package com.diplomska.sportsaway.events_data.repository

import com.diplomska.sportsaway.events_data.model.EventsResponse

interface SportsEventsRepository {

  suspend fun fetchAllEvents(): List<EventsResponse>

  fun addTeamsToFireBase()

  fun addEventsToFirebase()
}