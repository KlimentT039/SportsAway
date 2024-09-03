package com.diplomska.sportsaway.feature.events.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.toMatch
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.feature.events.view.model.GroupedMatch

class EventsUseCase(
  private val repository: SportsEventsRepository
) : ErrorHandlingUseCase() {

  suspend operator fun invoke(): Either<BaseError, List<GroupedMatch>> = lift {
    val matches = repository.getMatches().map { it.toMatch() }
    groupMatches(matches)
  }
}

private fun groupMatches(matches: List<Match>): List<GroupedMatch> {
  return matches.groupBy { it.competition }
    .mapNotNull { (competition, matches) ->
      GroupedMatch(competition, matches)
    }
}