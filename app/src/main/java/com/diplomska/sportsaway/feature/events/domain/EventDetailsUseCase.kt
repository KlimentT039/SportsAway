package com.diplomska.sportsaway.feature.events.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.toMatch
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository

class EventDetailsUseCase(private val sportsEventsRepository: SportsEventsRepository) :
  ErrorHandlingUseCase() {

  suspend operator fun invoke(id: Int): Either<BaseError, Match> = lift {
    sportsEventsRepository.getMatchById(id).toMatch()
  }

}