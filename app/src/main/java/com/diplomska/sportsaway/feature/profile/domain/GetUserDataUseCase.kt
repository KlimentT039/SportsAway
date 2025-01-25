package com.diplomska.sportsaway.feature.profile.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.errorhandling.map
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.utils.DATE_FORMAT
import com.diplomska.sportsaway.data.authentication_data.model.User
import com.diplomska.sportsaway.data.authentication_data.repository.FirebaseRepository
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import com.diplomska.sportsaway.feature.profile.model.UserData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetUserDataUseCase(private val firebaseRepository: FirebaseRepository) :
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

  fun logout(): Either<BaseError, Unit> =
    firebaseRepository.logout()

  private fun User.mapToUserData(): UserData {
    val separateMatches = separateMatchesWithStringDates(matches, LocalDate.now())
    return UserData(
      username = name,
      visitedMatches = separateMatches.first,
      upcomingMatches = separateMatches.second
    )
  }

  private fun separateMatchesWithStringDates(
    matches: List<Match>,
    currentDate: LocalDate
  ): Pair<List<Match>, List<Match>> {
    val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    return matches.partition { match ->
      val matchDate = LocalDate.parse(match.date, dateFormatter)
      matchDate.isBefore(currentDate) || matchDate.isEqual(currentDate)
    }
  }

}