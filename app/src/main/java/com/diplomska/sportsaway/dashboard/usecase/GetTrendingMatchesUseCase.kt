package com.diplomska.sportsaway.dashboard.usecase

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.shared.errorhandling.BaseError
import com.diplomska.sportsaway.shared.errorhandling.Either
import com.diplomska.sportsaway.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.shared.model.Match
import com.diplomska.sportsaway.shared.model.toMatch

class GetTrendingMatchesUseCase(
  private val repository: SportsEventsRepository
) : ErrorHandlingUseCase() {

  suspend operator fun invoke(limit: Int): Either<BaseError, List<Match>> = lift {
    val matches = repository.getMatches().map { it.toMatch() }
    matches.filter { it.trending }.take(limit)
  }

  fun getTeams() = repository.addTeamsToFireBase()

  fun getEvents() = repository.addEventsToFirebase()
}