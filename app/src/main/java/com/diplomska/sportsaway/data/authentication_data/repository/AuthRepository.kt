package com.diplomska.sportsaway.data.authentication_data.repository

import android.util.Log
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.data.authentication_data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {

  private val auth: FirebaseAuth = FirebaseAuth.getInstance()
  private val db = FirebaseFirestore.getInstance()

  suspend fun loginUser(email: String, password: String): Either<BaseError, String> {
    return withContext(Dispatchers.IO) {
      try {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        Either.Success(email)
      } catch (e: Exception) {
        Either.Failure(BaseError.AuthenticationError(e.message ?: "Login error"))
      }
    }
  }

  suspend fun createUser(
    name: String,
    password: String,
    email: String
  ): Either<BaseError, Unit> {
    return withContext(Dispatchers.IO) {
      try {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        val user = User(mail = email, username = name, favouriteTeams = emptyList())
        storeInDatabase(user)
        Either.Success(Unit)
      } catch (e: Exception) {
        Either.Failure(BaseError.AuthenticationError(e.message ?: "Login error"))
      }
    }
  }

  fun isLogged(): Boolean {
    return auth.currentUser != null
  }

  suspend fun getCurrentUser(): Either<BaseError, User> {
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

  suspend fun updateFavouritesList(teams: List<Int>): Either<BaseError, User> {
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

  private fun storeInDatabase(user: User) {
    val updateUser = hashMapOf(
      "name" to user.username,
      "mail" to user.mail,
      "favouriteTeams" to user.favouriteTeams,
    )
    db.collection("Users").document(user.mail)
      .set(updateUser)
      .addOnSuccessListener {
        Log.d(
          "$TAG - - - database",
          "${user.username} is added in database"
        )
      }
      .addOnFailureListener {
        Log.d(
          "$TAG - - - database",
          "${user.username} can not be added in database"
        )
      }
  }

  companion object {
    private const val TAG = "FirebaseAuthManager"
  }
}