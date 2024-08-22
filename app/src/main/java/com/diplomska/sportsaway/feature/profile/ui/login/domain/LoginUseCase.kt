package com.diplomska.sportsaway.feature.profile.ui.login.domain

import com.diplomska.sportsaway.data.profile_data.repository.AuthRepository
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either

class LoginUseCase(private val authRepository: AuthRepository) {

  suspend operator fun invoke(email: String, password: String): Either<BaseError, String> {
    return authRepository.loginUser(email, password)
  }
}