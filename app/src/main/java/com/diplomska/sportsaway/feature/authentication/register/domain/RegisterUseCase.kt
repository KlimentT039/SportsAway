package com.diplomska.sportsaway.feature.authentication.register.domain

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {

  suspend operator fun invoke(
    email: String,
    password: String,
    name: String
  ): Either<BaseError, Unit> {
    return authRepository.createUser(name = name, password = password, email = email)
  }
}