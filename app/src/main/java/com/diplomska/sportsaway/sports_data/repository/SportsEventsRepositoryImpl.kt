package com.diplomska.sportsaway.sports_data.repository

import android.content.Context
import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.sports_data.model.EventsResponse
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
class SportsEventsRepositoryImpl : SportsEventsRepository, ErrorHandlingUseCase() {

  private val db = FirebaseFirestore.getInstance()
  private val eventsRef: CollectionReference = db.collection("events")

  override suspend fun fetchAllEvents(): List<EventsResponse> {
    val querySnapshot: QuerySnapshot = eventsRef.get().await()
    return querySnapshot.toObjects(EventsResponse::class.java)
  }

  override suspend fun addTeamsToFireBase(context: Context) {
    TODO("Not yet implemented")
  }
}