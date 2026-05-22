package com.diplomska.sportsaway.feature.favourite.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.errorhandling.getOrElse
import com.diplomska.sportsaway.common.shared.errorhandling.map
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.shared.model.toMatch
import com.diplomska.sportsaway.common.shared.model.toTeam
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.feature.favourite.model.FavouriteTeam
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class TeamsUseCase(
  private val sportsRepository: SportsEventsRepository,
  private val firebaseRepository: AuthRepository
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

  suspend fun getFavouriteEvents(listOfIds: List<Int>): Either<BaseError, List<FavouriteTeam>> =
    coroutineScope {
      val deferredResult = listOfIds.map { id ->
        async {
          lift {
            val matches = sportsRepository.getMatchesByTeam(id).map {
              it.toMatch()
            }
            if (matches.isEmpty()) {
              FavouriteTeam(favouriteTeam = getTeamFromBackend(id), matches = matches)
            } else {
              FavouriteTeam(favouriteTeam = getTeamById(id, matches.first()), matches = matches)
            }
          }
        }
      }

      val allMatches = deferredResult.awaitAll()
      val failures = allMatches.filterIsInstance<Either.Failure<BaseError>>()
      val successfulResults = allMatches.filterIsInstance<Either.Success<FavouriteTeam>>()
      if (failures.isNotEmpty()) {
        Either.Failure(BaseError.UnknownError)
      } else {
        Either.Success(successfulResults.map { it.value })
      }
    }

  suspend fun updateListOfFavourites(teams: List<Int>): Either<BaseError, Unit> = lift {
    firebaseRepository.updateFavouritesList(teams)
  }

  private suspend fun getListOfFavouriteTeams(): Either<BaseError, List<Int>> {
    return firebaseRepository.getCurrentUser().map { user ->
      user.favouriteTeams
    }
  }

  private fun isTeamInTheListOfFavourites(id: Int, favouriteTeams: List<Int>): Boolean {
    return favouriteTeams.contains(id)
  }

  private fun getTeamById(id: Int, match: Match): Team {
    return if (match.homeTeam.id == id) match.homeTeam else match.awayTeam
  }

  private suspend fun getTeamFromBackend(id: Int): Team {
    return sportsRepository.getTeamById(id).toTeam()
  }
}
