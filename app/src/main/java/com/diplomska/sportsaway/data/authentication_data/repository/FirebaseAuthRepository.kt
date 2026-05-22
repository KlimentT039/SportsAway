package com.diplomska.sportsaway.data.authentication_data.repository

import android.util.Log
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.data.authentication_data.model.User
import com.diplomska.sportsaway.feature.authentication.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthRepository : AuthRepository {

  private val auth: FirebaseAuth = FirebaseAuth.getInstance()
  private val db = FirebaseFirestore.getInstance()


  override suspend fun loginUser(email: String, password: String): Either<BaseError, String> {
    return withContext(Dispatchers.IO) {
      try {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        Either.Success(email)
      } catch (e: FirebaseAuthInvalidUserException) {
        Either.Failure(BaseError.AuthenticationError(e.message ?: Constants.EXTRA_INVALID_USER))
      } catch (e: FirebaseAuthInvalidCredentialsException) {
        Either.Failure(BaseError.AuthenticationError(e.message ?: Constants.EXTRA_INVALID_PASS))
      } catch (e: Exception) {
        Either.Failure(BaseError.UnknownError)
      }
    }
  }

  override suspend fun createUser(
    name: String,
    password: String,
    email: String
  ): Either<BaseError, Unit> {
    return withContext(Dispatchers.IO) {
      try {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        val user = User(mail = email, name = name, favouriteTeams = emptyList())
        storeInDatabase(user)
        Either.Success(Unit)
      } catch (e: Exception) {
        Either.Failure(BaseError.AuthenticationError(e.message ?: "Login error"))
      }
    }
  }

  override fun isLogged(): Boolean {
    return auth.currentUser != null
  }

  override fun logout(): Either<BaseError, Unit> {
    return try {
      FirebaseAuth.getInstance().signOut()
      Either.Success(Unit)
    } catch (e: Exception) {
      Either.Failure(BaseError.AuthenticationError(e.message ?: "Login error"))
    }
  }

  override suspend fun getCurrentUser(): Either<BaseError, User> {
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
      ?: return Either.Failure(BaseError.AuthenticationError("No user is logged in"))

    return try {
      val documentSnapshot = db.collection("Users")
        .document(currentUserEmail)
        .get()
        .await()

      val user = documentSnapshot.toObject(User::class.java)
        ?: return Either.Failure(BaseError.UnknownError)

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

    try {
      storeInDatabase(updatedUser)
      return Either.Success(updatedUser)
    } catch (e: Exception) {
      return Either.Failure(BaseError.UnknownError)
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

    try {
      storeInDatabase(updatedUser)
      return Either.Success(updatedUser)
    } catch (e: Exception) {
      return Either.Failure(BaseError.UnknownError)
    }
  }

  private fun storeInDatabase(user: User) {
    val updateUser = hashMapOf(
      "name" to user.name,
      "mail" to user.mail,
      "favouriteTeams" to user.favouriteTeams,
      "matches" to user.matches
    )
    db.collection("Users").document(user.mail)
      .set(updateUser)
      .addOnSuccessListener {
        Log.d(
          "$TAG - - - database",
          "${user.name} is added in database"
        )
      }
      .addOnFailureListener {
        Log.d(
          "$TAG - - - database",
          "${user.name} can not be added in database"
        )
      }
  }

  companion object {
    private const val TAG = "FirebaseAuthManager"
  }
}
