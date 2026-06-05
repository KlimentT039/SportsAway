package com.diplomska.sportsaway.data.authentication_data.repository

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.data.authentication_data.model.User

/**
 * Phase 1 stub so iOS can boot without the native Firebase SDK linked.
 * Replace with a real Firebase implementation once `firebase-ios-sdk` is integrated.
 */
class StubAuthRepository : AuthRepository {
  override suspend fun loginUser(email: String, password: String): Either<BaseError, String> =
    Either.Failure(BaseError.AuthenticationError("Auth not yet implemented on iOS"))

  override suspend fun createUser(name: String, password: String, email: String): Either<BaseError, Unit> =
    Either.Failure(BaseError.AuthenticationError("Auth not yet implemented on iOS"))

  override fun isLogged(): Boolean = false

  override suspend fun logout(): Either<BaseError, Unit> = Either.Success(Unit)

  override suspend fun getCurrentUser(): Either<BaseError, User> =
    Either.Failure(BaseError.AuthenticationError("Auth not yet implemented on iOS"))

  override suspend fun updateFavouritesList(teams: List<Int>): Either<BaseError, User?> =
    Either.Success(null)

  override suspend fun addMatchToUserDatabase(match: Match): Either<BaseError, User?> =
    Either.Success(null)
}
