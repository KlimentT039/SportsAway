package com.diplomska.sportsaway.events.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.events.model.Match
import com.diplomska.sportsaway.shared.errorhandling.BaseError
import com.diplomska.sportsaway.shared.errorhandling.Either
import com.diplomska.sportsaway.sports_data.repository.SportsEventsRepository

class EventsUseCase(
  private val repository: SportsEventsRepository,
  private val mapEventsResponseToMatch: MapEventsResponseToMatch
) : ErrorHandlingUseCase() {

  suspend operator fun invoke(): Either<BaseError, List<Match>> = lift {
    repository.fetchAllEvents().map {
      mapEventsResponseToMatch(it)
    }
  }
}