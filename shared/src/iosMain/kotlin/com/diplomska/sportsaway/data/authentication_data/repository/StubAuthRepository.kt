package com.diplomska.sportsaway.data.authentication_data.repository

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.data.authentication_data.model.PersistedMatch
import com.diplomska.sportsaway.data.authentication_data.model.User
import com.diplomska.sportsaway.data.authentication_data.model.toPersistedMatch

/**
 * In-memory auth so the iOS app can exercise the full sign-up / login / favourites / profile
 * flow without the Firebase iOS SDK linked. Data lives for the lifetime of the process —
 * intentionally not persisted; replace with a real Firebase impl before shipping.
 */
class StubAuthRepository : AuthRepository {

  private data class Account(val user: User, val password: String)

  private val accounts = mutableMapOf<String, Account>()
  private var currentEmail: String? = null

  override suspend fun loginUser(email: String, password: String): Either<BaseError, String> {
    val account = accounts[email.lowercase()]
      ?: return Either.Failure(BaseError.AuthenticationError("No account for $email"))
    if (account.password != password) {
      return Either.Failure(BaseError.AuthenticationError("Wrong password"))
    }
    currentEmail = email.lowercase()
    return Either.Success(account.user.name)
  }

  override suspend fun createUser(name: String, password: String, email: String): Either<BaseError, Unit> {
    val key = email.lowercase()
    if (accounts.containsKey(key)) {
      return Either.Failure(BaseError.AuthenticationError("Account already exists"))
    }
    accounts[key] = Account(User(mail = email, name = name), password)
    currentEmail = key
    return Either.Success(Unit)
  }

  override fun isLogged(): Boolean = currentEmail != null

  override suspend fun logout(): Either<BaseError, Unit> {
    currentEmail = null
    return Either.Success(Unit)
  }

  override suspend fun getCurrentUser(): Either<BaseError, User> {
    val account = currentAccount()
      ?: return Either.Failure(BaseError.AuthenticationError("Not signed in"))
    return Either.Success(account.user)
  }

  override suspend fun updateFavouritesList(teams: List<Int>): Either<BaseError, User?> {
    val account = currentAccount() ?: return Either.Success(null)
    val updated = account.user.copy(favouriteTeams = teams)
    accounts[currentEmail!!] = account.copy(user = updated)
    return Either.Success(updated)
  }

  override suspend fun addMatchToUserDatabase(match: Match): Either<BaseError, User?> {
    val account = currentAccount() ?: return Either.Success(null)
    val newMatch: PersistedMatch = match.toPersistedMatch()
    if (account.user.matches.any { it.id == newMatch.id }) {
      return Either.Success(account.user)
    }
    val updated = account.user.copy(matches = account.user.matches + newMatch)
    accounts[currentEmail!!] = account.copy(user = updated)
    return Either.Success(updated)
  }

  private fun currentAccount(): Account? = currentEmail?.let { accounts[it] }
}
