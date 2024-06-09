package com.diplomska.sportsaway.events_data.repository

import com.diplomska.sportsaway.events_data.model.CompetitionResponse
import com.diplomska.sportsaway.events_data.model.EventsResponse
import com.diplomska.sportsaway.events_data.model.MatchListResponse
import com.diplomska.sportsaway.events_data.model.MatchResponse

interface SportsEventsRepository {

  suspend fun fetchAllEvents(): List<EventsResponse>

  fun addTeamsToFireBase()

  fun addEventsToFirebase()

  suspend fun getMatches(): List<MatchResponse>

  suspend fun getCompetitions(): List<CompetitionResponse>
}