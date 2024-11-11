package com.diplomska.sportsaway.feature.favourite.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.errorhandling.getOrElse
import com.diplomska.sportsaway.common.shared.errorhandling.map
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.shared.model.toTeam
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.feature.events.view.model.GroupedMatch
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class TeamsUseCase(
  private val sportsRepository: SportsEventsRepository,
  private val authRepository: AuthRepository
) : ErrorHandlingUseCase() {

  suspend operator fun invoke(): Either<BaseError, List<Team>> {
    val favouriteTeams =
      getListOfFavouriteTeams().getOrElse { return Either.Failure(BaseError.UnknownError) }
    return lift {
      sportsRepository.getTeams().teams.map {
        it.toTeam().copy(isFavourite = isTeamInTheListOfFavourites(it.id, favouriteTeams))
      }
    }
  }

  suspend fun getFavouriteEvents(listOfIds: List<Int>) = coroutineScope {

    val deferredResult = listOfIds.map { id ->
      async {
        sportsRepository.getMatchesByTeam(id).map {

        }
      }
    }

    val allMatches = deferredResult.awaitAll()
    val events = allMatches.map { it }
    val failures = allMatches.filterIsInstance<Either.Failure<BaseError>>()

  }

  suspend fun updateListOfFavourites(teams: List<Int>): Either<BaseError, Unit> = lift {
    authRepository.updateFavouritesList(teams)
  }

  private suspend fun getListOfFavouriteTeams(): Either<BaseError, List<Int>> {
    return authRepository.getCurrentUser().map { user ->
      user.favouriteTeams
    }
  }

  private fun isTeamInTheListOfFavourites(id: Int, favouriteTeams: List<Int>): Boolean {
    return favouriteTeams.contains(id)
  }
}
