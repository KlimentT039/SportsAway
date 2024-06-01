package com.diplomska.sportsaway.events_data.repository

import android.util.Log
import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.events_data.model.EventsResponse
import com.diplomska.sportsaway.events_data.provider.EventsJsonProvider
import com.diplomska.sportsaway.events_data.provider.TeamJsonProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class SportsEventsRepositoryImpl(
  private val teamJsonProvider: TeamJsonProvider,
  private val eventsJsonProvider: EventsJsonProvider
) : SportsEventsRepository, ErrorHandlingUseCase() {

  private val TAG = "SportsRepository"

  private val db = FirebaseFirestore.getInstance()
  private val eventsRef: CollectionReference = db.collection("events")

  override suspend fun fetchAllEvents(): List<EventsResponse> {
    val querySnapshot: QuerySnapshot = eventsRef.get().await()
    return querySnapshot.toObjects(EventsResponse::class.java)
  }

  override fun addTeamsToFireBase() {
    val teamsList = teamJsonProvider.teams
    for (team in teamsList) {
      db.collection("teams").document(team.name).set(team).addOnSuccessListener {
        Log.d(
          "$TAG - - - database",
          "${team.name} is added in database"
        )
      }
        .addOnFailureListener {
          Log.d(
            "$TAG - - - database",
            "${team.name} can not be added in database"
          )
        }
    }
  }

  override fun addEventsToFirebase() {
    val events = eventsJsonProvider.events
    for (event in events) {
      db.collection("events").document(event.id).set(event).addOnSuccessListener {
        Log.d(
          "$TAG - - - database",
          "${event.homeTeam} vs ${event.awayTeam} is added in database"
        )
      }.addOnFailureListener {
          Log.d(
            "$TAG - - - database",
            "$${event.homeTeam} vs ${event.awayTeam}  can not be added in database"
          )
        }
    }
  }
}