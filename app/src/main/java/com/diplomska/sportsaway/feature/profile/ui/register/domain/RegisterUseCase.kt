package com.diplomska.sportsaway.feature.profile.ui.register.domain

import com.diplomska.sportsaway.data.profile_data.repository.AuthRepository
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either

class RegisterUseCase(private val authRepository: AuthRepository) {

  suspend operator fun invoke(
    email: String,
    password: String,
    name: String
  ): Either<BaseError, Unit> {
    return authRepository.createUser(name = name, password = password, email = email)
  }
}