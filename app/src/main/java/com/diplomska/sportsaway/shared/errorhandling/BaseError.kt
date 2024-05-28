package com.diplomska.sportsaway.shared.errorhandling

sealed interface BaseError {
  data object UnknownError : BaseError
  data class AuthenticationError(val message: String): BaseError
}

