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

  suspend operator fun invoke(competitionId: Int?): Either<BaseError, List<GroupedMatch>> = lift {
    val matches = repository.getMatches(competitionId).map { it.toMatch() }
    groupMatches(matches)
  }

  fun searchQuery(query: String, groupedMatches: List<GroupedMatch>): List<GroupedMatch> {
    return groupedMatches.map { groupedMatch ->
      val sortedMatches = groupedMatch.matches.filter {
        it.homeTeam.name.contains(query, ignoreCase = true) || it.awayTeam.name.contains(
          query,
          ignoreCase = true
        )
      }

      groupedMatch.copy(matches = sortedMatches, showSection = sortedMatches.isNotEmpty())
    }
  }
}

private fun groupMatches(matches: List<Match>): List<GroupedMatch> {
  return matches.groupBy { it.competition }
    .mapNotNull { (competition, matches) ->
      GroupedMatch(competition, matches, showSection = matches.isNotEmpty())
    }
}
