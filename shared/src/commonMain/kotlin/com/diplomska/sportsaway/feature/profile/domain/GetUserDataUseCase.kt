package com.diplomska.sportsaway.feature.profile.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.errorhandling.map
import com.diplomska.sportsaway.common.shared.utils.DATE_FORMAT
import com.diplomska.sportsaway.data.authentication_data.model.PersistedMatch
import com.diplomska.sportsaway.data.authentication_data.model.User
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import com.diplomska.sportsaway.feature.profile.model.UserData
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.todayIn

class GetUserDataUseCase(private val firebaseRepository: AuthRepository) :
  ErrorHandlingUseCase() {

  suspend fun getUserStatus(): Either<BaseError, ProfileViewState> {
    return if (!firebaseRepository.isLogged()) {
      Either.Success(ProfileViewState.UserHasNotLoggedIn)
    } else {
      firebaseRepository.getCurrentUser().map { user ->
        ProfileViewState.ProfileData(user.mapToUserData())
      }
    }
  }

  suspend fun logout(): Either<BaseError, Unit> =
    firebaseRepository.logout()

  private fun User.mapToUserData(): UserData {
    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val separateMatches = separateMatchesWithStringDates(matches, currentDate)
    return UserData(
      username = name,
      visitedMatches = separateMatches.first,
      upcomingMatches = separateMatches.second
    )
  }

  private fun separateMatchesWithStringDates(
    matches: List<PersistedMatch>,
    currentDate: LocalDate
  ): Pair<List<PersistedMatch>, List<PersistedMatch>> {
    val dateFormat = LocalDate.Format {
      dayOfMonth()
      char('.')
      monthName(MonthNames.ENGLISH_ABBREVIATED)
      char('.')
      year()
    }

    return matches.partition { match ->
      val matchDate = LocalDate.parse(match.date, dateFormat)
      matchDate <= currentDate
    }
  }
}
