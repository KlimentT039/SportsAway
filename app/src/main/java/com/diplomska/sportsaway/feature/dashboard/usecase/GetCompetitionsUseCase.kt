package com.diplomska.sportsaway.feature.dashboard.usecase

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Competition
import com.diplomska.sportsaway.common.shared.model.toCompetition
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository

class GetCompetitionsUseCase(private val sportsRepo: SportsEventsRepository) :
  ErrorHandlingUseCase() {

  suspend operator fun invoke(): Either<BaseError, List<Competition>> = lift {
    sportsRepo.getCompetitions().map {
      it.toCompetition()
    }
  }

}