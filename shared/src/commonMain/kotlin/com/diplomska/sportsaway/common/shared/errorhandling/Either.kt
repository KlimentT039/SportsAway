package com.diplomska.sportsaway.common.shared.errorhandling

sealed class Either<out F, out S> {
  class Failure<F>(val value: F) : Either<F, Nothing>()
  class Success<S>(val value: S) : Either<Nothing, S>()
}

inline fun <F, S, T> Either<F, S>.fold(onFailure: (F) -> T, onSuccess: (S) -> T): T =
  when (this) {
    is Either.Failure -> onFailure(value)
    is Either.Success -> onSuccess(value)

  }

fun <F, S, T> Either<F, S>.map(block: (S) -> T): Either<F, T> =
  when (this) {
    is Either.Failure -> this
    is Either.Success -> Either.Success(block(value))
  }

inline fun <F, S> Either<F, S>.getOrElse(onFailure: (F) -> S): S =
  when (this) {
    is Either.Failure -> onFailure(value)
    is Either.Success -> value
  }
