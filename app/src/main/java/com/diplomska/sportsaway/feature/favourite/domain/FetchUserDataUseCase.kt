package com.diplomska.sportsaway.feature.favourite.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewState

class FetchUserDataUseCase(
  private val authRepository: AuthRepository
) : ErrorHandlingUseCase() {

  fun isTheUserLoggedIn() = authRepository.isLogged()

  suspend fun fetchUsersFavouriteTeam(): FavouriteViewState {
    return authRepository.getCurrentUser().fold(
      onSuccess = {
        if (it.favouriteTeams.isEmpty()) {
          FavouriteViewState.HasNotSelectedTeams
        } else {
          FavouriteViewState.FavouriteTeams(it.favouriteTeams)
        }
      },
      onFailure = {
        FavouriteViewState.ShowError
      }
    )
  }
}