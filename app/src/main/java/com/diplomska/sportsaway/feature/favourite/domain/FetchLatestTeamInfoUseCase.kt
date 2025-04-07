package com.diplomska.sportsaway.feature.favourite.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.shared.model.toTeam
import com.diplomska.sportsaway.data.events_data.model.Player
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.feature.favourite.model.LatestTeamInfo

class FetchLatestTeamInfoUseCase(private val sportsEventsRepository: SportsEventsRepository) :
  ErrorHandlingUseCase() {

  suspend operator fun invoke(id: Int): Either<BaseError, LatestTeamInfo> {
    val teamInfo = sportsEventsRepository.fetchLatestTeamInfo(id)
      ?: return Either.Failure(BaseError.UnknownError)

    return when (val team = getTeamsSquad(id)) {
      is Either.Success -> Either.Success(
        LatestTeamInfo(
          team.value,
          teamInfo = teamInfo,
        )
      )

      is Either.Failure -> Either.Failure((BaseError.UnknownError))
    }
  }

  private suspend fun getTeamsSquad(id: Int): Either<BaseError, Team> {
    return lift {
      sportsEventsRepository.getTeamById(id).toTeam()
    }
  }
}