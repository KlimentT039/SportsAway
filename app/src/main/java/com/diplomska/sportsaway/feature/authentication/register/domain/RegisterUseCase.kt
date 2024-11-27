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
}