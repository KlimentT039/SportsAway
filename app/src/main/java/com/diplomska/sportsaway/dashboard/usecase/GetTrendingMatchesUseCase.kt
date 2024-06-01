package com.diplomska.sportsaway.dashboard.usecase

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.shared.errorhandling.BaseError
import com.diplomska.sportsaway.shared.errorhandling.Either
import com.diplomska.sportsaway.events.model.Match
import com.diplomska.sportsaway.events.domain.MapEventsResponseToMatch
import com.diplomska.sportsaway.events_data.repository.SportsEventsRepository

class GetTrendingMatchesUseCase(
  private val repository: SportsEventsRepository,
  private val mapEventsResponseToMatch: MapEventsResponseToMatch
) : ErrorHandlingUseCase() {

  suspend operator fun invoke(limit: Int): Either<BaseError, List<Match>> = lift {
    repository.fetchAllEvents().filter {
      it.trending
    }.map {
      mapEventsResponseToMatch(it)
    }.take(limit).sortedBy { it.sport }
  }

  fun getTeams() = repository.addTeamsToFireBase()

  fun getEvents() = repository.addEventsToFirebase()
}