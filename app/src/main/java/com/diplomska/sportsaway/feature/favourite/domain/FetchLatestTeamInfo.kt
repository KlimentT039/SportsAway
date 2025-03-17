package com.diplomska.sportsaway.feature.favourite.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.errorhandling.getOrElse
import com.diplomska.sportsaway.data.events_data.model.Player
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.feature.favourite.model.LatestTeamInfo

class FetchLatestTeamInfo(private val sportsEventsRepository: SportsEventsRepository) :
  ErrorHandlingUseCase() {

  suspend operator fun invoke(id: Int): Either<BaseError, LatestTeamInfo> {
    val teamInfo = sportsEventsRepository.fetchLatestTeamInfo(id)
      ?: return Either.Failure(BaseError.UnknownError)

    return when (val squadResult = getTeamsSquad(id)) {
      is Either.Success -> Either.Success(LatestTeamInfo(teamInfo, squadResult.value))
      is Either.Failure -> Either.Failure((BaseError.UnknownError))
    }
  }

  private suspend fun getTeamsSquad(id: Int): Either<BaseError, List<Player>> =
    sportsEventsRepository.getTeamById(id).squad.takeIf { !it.isNullOrEmpty() }
      ?.let { Either.Success(it) }
      ?: Either.Failure(BaseError.UnknownError)

}