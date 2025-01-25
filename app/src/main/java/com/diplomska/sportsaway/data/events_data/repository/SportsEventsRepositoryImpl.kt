package com.diplomska.sportsaway.data.events_data.repository

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.data.events_data.model.EventsResponse
import com.diplomska.sportsaway.data.events_data.model.MatchResponse
import com.diplomska.sportsaway.data.events_data.model.StadiumResponse
import com.diplomska.sportsaway.data.events_data.model.Venue
import com.diplomska.sportsaway.data.events_data.model.TeamResponse
import com.diplomska.sportsaway.data.events_data.model.TeamsResponse
import com.diplomska.sportsaway.data.events_data.model.listOfCompetitionIds
import com.diplomska.sportsaway.data.events_data.network.SportsApi
import com.diplomska.sportsaway.data.events_data.network.StadiumApi
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SportsEventsRepositoryImpl(
  private val sportsApi: SportsApi,
  private val stadiumApi: StadiumApi
) : SportsEventsRepository, ErrorHandlingUseCase() {

  private val TAG = "SportsRepository"

  private val db = FirebaseFirestore.getInstance()
  private val eventsRef: CollectionReference = db.collection("events")

  override suspend fun getMatches(competitionId: Int?): List<MatchResponse> {
    val getDates = getTwoWeeksDates()
    return sportsApi.getMatches(
      dateTo = getDates.second,
      dateFrom = getDates.first,
      competitions = competitionId?.toString() ?: listOfCompetitionIds.joinToString(",")
    ).matches
  }

  override suspend fun getCompetitions() = sportsApi.getCompetitions().competitions

  override suspend fun fetchAllEvents(): List<EventsResponse> {
    val querySnapshot: QuerySnapshot = eventsRef.get().await()
    return querySnapshot.toObjects(EventsResponse::class.java)
  }

  override suspend fun getTeams(): TeamsResponse {
    return sportsApi.getTeams(limit = 100)
  }

  override suspend fun getMatchesByTeam(id: Int): List<MatchResponse> {
    val getDates = getTwoWeeksDates()
    return sportsApi.getMatchesByTeam(
      teamId = id,
      dateFrom = getDates.first,
      dateTo = getDates.second
    ).matches
  }

  override suspend fun getMatchById(id: Int): MatchResponse {
    return sportsApi.getMatchDetails(id)
  }

  override suspend fun getStadiumPic(stadiumName: String): StadiumResponse {
    return stadiumApi.searchVenues(stadiumName)
  }

  override suspend fun getTeamById(id: Int): TeamResponse {
    return sportsApi.getTeamsById(id)
  }

  private fun getTwoWeeksDates(): Pair<String, String> {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateFrom = LocalDate.now().plusWeeks(1).format(dateFormatter)
    val dateTo = LocalDate.now().plusWeeks(1).plusDays(5).format(dateFormatter)
    return dateFrom to dateTo
  }

}