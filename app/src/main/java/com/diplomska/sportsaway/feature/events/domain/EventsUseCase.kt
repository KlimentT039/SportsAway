package com.diplomska.sportsaway.feature.events.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.toMatch
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository

class EventsUseCase(
  private val repository: SportsEventsRepository
) : ErrorHandlingUseCase() {

  suspend operator fun invoke(): Either<BaseError, List<Match>> = lift {
    repository.getMatches().map { it.toMatch() }
  }
}