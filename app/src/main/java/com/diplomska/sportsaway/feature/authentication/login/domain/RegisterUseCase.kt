package com.diplomska.sportsaway.feature.authentication.login.domain

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {

  suspend operator fun invoke(
    name: String,
    password: String,
    email: String
  ): Either<BaseError, Unit> {
    return authRepository.createUser(name, password, email)
  }
}