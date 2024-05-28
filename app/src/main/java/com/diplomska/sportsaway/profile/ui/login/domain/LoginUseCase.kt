package com.diplomska.sportsaway.profile.ui.login.domain

import com.diplomska.sportsaway.profile_data.repository.AuthRepository
import com.diplomska.sportsaway.shared.errorhandling.BaseError
import com.diplomska.sportsaway.shared.errorhandling.Either

class LoginUseCase(private val authRepository: AuthRepository) {

  suspend operator fun invoke(email: String, password: String): Either<BaseError, String> {
    return authRepository.loginUser(email, password)
  }
}