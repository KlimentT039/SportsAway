package com.diplomska.sportsaway.feature.dashboard.usecase

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.toMatch
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository

class GetTrendingMatchesUseCase(
  private val repository: SportsEventsRepository
) : ErrorHandlingUseCase() {

  suspend operator fun invoke(limit: Int): Either<BaseError, List<Match>> = lift {
    val matches = repository.getMatches().map { it.toMatch() }
    val trending = matches.filter { it.trending }.take(limit)
    if (trending.isNotEmpty()) trending else matches.take(limit)
  }

}
