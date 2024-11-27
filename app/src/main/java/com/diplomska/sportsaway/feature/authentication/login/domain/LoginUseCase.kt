package com.diplomska.sportsaway.feature.authentication.login.domain

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.data.authentication_data.repository.FirebaseRepository

class LoginUseCase(private val firebaseRepository: FirebaseRepository) {

  suspend operator fun invoke(email: String, password: String): Either<BaseError, String> {
    return firebaseRepository.loginUser(email, password)
  }
}