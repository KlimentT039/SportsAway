package com.diplomska.sportsaway.events.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.shared.errorhandling.BaseError
import com.diplomska.sportsaway.shared.errorhandling.Either
import com.diplomska.sportsaway.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.shared.model.Match
import com.diplomska.sportsaway.shared.model.toMatch

class EventsUseCase(
  private val repository: SportsEventsRepository
) : ErrorHandlingUseCase() {

  suspend operator fun invoke(): Either<BaseError, List<Match>> = lift {
    repository.getMatches().map { it.toMatch() }
  }
}