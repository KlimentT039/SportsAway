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
        val user = User(mail = email, username = name, favouriteTeam = null)
        storeInDatabase(user)
        Either.Success(Unit)
      } catch (e: Exception) {
        Either.Failure(BaseError.AuthenticationError(e.message ?: "Login error"))
      }
    }
  }

  private fun storeInDatabase(user: User) {
    val updateUser = hashMapOf(
      "name" to user.username,
      "mail" to user.mail,
      "favouriteTeam" to user.favouriteTeam
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

  fun isLogged(): Boolean {
    return auth.currentUser != null
  }

  companion object {
    private const val TAG = "FirebaseAuthManager"
  }
}