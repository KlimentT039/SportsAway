package com.diplomska.sportsaway.data.authentication_data.repository

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.data.authentication_data.model.User

interface AuthRepository {

  suspend fun loginUser(email: String, password: String): Either<BaseError, String>

  suspend fun createUser(name: String, password: String, email: String): Either<BaseError, Unit>

  fun isLogged(): Boolean

  suspend fun logout(): Either<BaseError, Unit>

  suspend fun getCurrentUser(): Either<BaseError, User>

  suspend fun updateFavouritesList(teams: List<Int>): Either<BaseError, User?>

  suspend fun addMatchToUserDatabase(match: Match): Either<BaseError, User?>
}
