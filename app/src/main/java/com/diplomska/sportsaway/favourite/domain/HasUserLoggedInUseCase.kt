package com.diplomska.sportsaway.favourite.domain

import com.diplomska.sportsaway.profile_data.repository.AuthRepository

class HasUserLoggedInUseCase(private val authRepository: AuthRepository) {

  fun isTheUserLoggedIn() = authRepository.isLogged()

}