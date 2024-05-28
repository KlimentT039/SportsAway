package com.diplomska.sportsaway.sports_data.repository

import android.content.Context
import com.diplomska.sportsaway.sports_data.model.EventsResponse

interface SportsEventsRepository {
  suspend fun fetchAllEvents() : List<EventsResponse>

  suspend fun addTeamsToFireBase(context: Context)
}