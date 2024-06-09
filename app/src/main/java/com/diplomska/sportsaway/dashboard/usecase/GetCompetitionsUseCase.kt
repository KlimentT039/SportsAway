package com.diplomska.sportsaway.dashboard.usecase

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.shared.errorhandling.BaseError
import com.diplomska.sportsaway.shared.errorhandling.Either
import com.diplomska.sportsaway.shared.model.Competition
import com.diplomska.sportsaway.shared.model.toCompetition

class GetCompetitionsUseCase(private val sportsRepo: SportsEventsRepository) :
  ErrorHandlingUseCase() {

  suspend operator fun invoke(): Either<BaseError, List<Competition>> = lift {
    sportsRepo.getCompetitions().map {
      it.toCompetition()
    }
  }

}