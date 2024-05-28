package com.diplomska.sportsaway.shared.errorhandling

sealed class Either<out F, out S> {
  class Failure<F>(val value: F) : Either<F, Nothing>()
  class Success<S>(val value: S) : Either<Nothing, S>()
}

inline fun <F, S, T> Either<F, S>.fold(onFailure: (F) -> T, onSuccess: (S) -> T): T =
  when (this) {
    is Either.Failure -> onFailure(value)
    is Either.Success -> onSuccess(value)
    else -> throw IllegalStateException("Unexpected state in com.diplomska.sportsaway.shared.errorhandling.Either com.diplomska.sportsaway.shared.errorhandling.fold")

  }

fun <F, S, T> Either<F, S>.map(block: (S) -> T): Either<F, T> =
  when (this) {
    is Either.Failure -> this
    is Either.Success -> Either.Success(block(value))
    else -> throw IllegalStateException("Unexpected state in com.diplomska.sportsaway.shared.errorhandling.Either com.diplomska.sportsaway.shared.errorhandling.fold")
  }
