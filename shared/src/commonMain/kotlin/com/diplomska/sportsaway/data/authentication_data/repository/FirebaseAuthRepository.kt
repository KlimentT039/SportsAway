package com.diplomska.sportsaway.data.authentication_data.repository

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.data.authentication_data.model.User
import com.diplomska.sportsaway.feature.authentication.utils.Constants
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class FirebaseAuthRepository : AuthRepository {

  private val auth = Firebase.auth
  private val db = Firebase.firestore

  override suspend fun loginUser(email: String, password: String): Either<BaseError, String> {
    return try {
      auth.signInWithEmailAndPassword(email, password)
      Either.Success(email)
    } catch (e: FirebaseAuthInvalidUserException) {
      Either.Failure(BaseError.AuthenticationError(e.message ?: Constants.EXTRA_INVALID_USER))
    } catch (e: FirebaseAuthInvalidCredentialsException) {
      Either.Failure(BaseError.AuthenticationError(e.message ?: Constants.EXTRA_INVALID_PASS))
    } catch (e: Exception) {
      Either.Failure(BaseError.UnknownError)
    }
  }

  override suspend fun createUser(
    name: String,
    password: String,
    email: String
  ): Either<BaseError, Unit> {
    return try {
      auth.createUserWithEmailAndPassword(email, password)
      val user = User(mail = email, name = name, favouriteTeams = emptyList())
      storeInDatabase(user)
      Either.Success(Unit)
    } catch (e: Exception) {
      Either.Failure(BaseError.AuthenticationError(e.message ?: "Login error"))
    }
  }

  override fun isLogged(): Boolean = auth.currentUser != null

  override suspend fun logout(): Either<BaseError, Unit> {
    return try {
      auth.signOut()
      Either.Success(Unit)
    } catch (e: Exception) {
      Either.Failure(BaseError.AuthenticationError(e.message ?: "Logout error"))
    }
  }

  override suspend fun getCurrentUser(): Either<BaseError, User> {
    val currentUserEmail = auth.currentUser?.email
      ?: return Either.Failure(BaseError.AuthenticationError("No user is logged in"))

    return try {
      val snapshot = db.collection("Users").document(currentUserEmail).get()
      val user = snapshot.data<User>()
      Either.Success(user)
    } catch (e: Exception) {
      Either.Failure(BaseError.UnknownError)
    }
  }

  override suspend fun updateFavouritesList(teams: List<Int>): Either<BaseError, User?> {
    val currentUserResult = getCurrentUser()
    if (currentUserResult is Either.Failure) {
      return currentUserResult
    }

    val currentUser = (currentUserResult as Either.Success).value
    val updatedUser = currentUser.copy(favouriteTeams = teams)

    return try {
      storeInDatabase(updatedUser)
      Either.Success(updatedUser)
    } catch (e: Exception) {
      Either.Failure(BaseError.UnknownError)
    }
  }

  override suspend fun addMatchToUserDatabase(match: Match): Either<BaseError, User?> {
    val currentUserResult = getCurrentUser()
    if (currentUserResult is Either.Failure) {
      return Either.Success(null)
    }
    val currentUser = (currentUserResult as Either.Success).value
    val updatedMatches = (currentUser.matches + match).sortedBy { it.date }
    val updatedUser = currentUser.copy(matches = updatedMatches)

    return try {
      storeInDatabase(updatedUser)
      Either.Success(updatedUser)
    } catch (e: Exception) {
      Either.Failure(BaseError.UnknownError)
    }
  }

  private suspend fun storeInDatabase(user: User) {
    db.collection("Users").document(user.mail).set(user)
  }
}
