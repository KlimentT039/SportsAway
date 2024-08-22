package com.diplomska.core.errorhandling

import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either

abstract class ErrorHandlingUseCase {


  inline fun <reified S> lift(
    errorHandler: DefaultErrorHandler = DefaultErrorHandler(),
    block: () -> S,
  ): Either<BaseError, S> =
    try {
      Either.Success(block())
    } catch (e: Exception) {
      // mark successful requests without body as success if return type is Unit
      if (e is KotlinNullPointerException && S::class.java == Unit::class.java) {
        Either.Success(Unit as S) // cast Unit to S to satisfy lint
      } else {
        Either.Failure(errorHandler.resolve(e))
      }
    }

}

open class DefaultErrorHandler {
  fun resolve(e: Exception): BaseError {
    return BaseError.UnknownError
  }
}