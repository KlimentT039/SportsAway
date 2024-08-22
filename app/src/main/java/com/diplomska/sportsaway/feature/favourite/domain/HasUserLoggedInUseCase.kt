package com.diplomska.sportsaway.feature.favourite.domain

import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository

class HasUserLoggedInUseCase(private val authRepository: AuthRepository) {

  fun isTheUserLoggedIn() = authRepository.isLogged()

}