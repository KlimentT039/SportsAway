package com.diplomska.sportsaway.feature.authentication.register.domain

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.data.authentication_data.repository.FirebaseRepository

class RegisterUseCase(private val firebaseRepository: FirebaseRepository) {

  suspend operator fun invoke(
    email: String,
    password: String,
    name: String
  ): Either<BaseError, Unit> {
    return firebaseRepository.createUser(name = name, password = password, email = email)
  }

  fun validateEmail(email: String): Boolean {
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
    return email.matches(emailRegex)
  }

  fun validatePassword(password: String): Boolean {
    val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\d\\s]).{6,30}$".toRegex()
    return password.matches(passwordRegex)
  }

  fun validateConfirmPassword(confirmPassword: String, password: String): Boolean =
    confirmPassword == password
}