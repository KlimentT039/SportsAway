package com.diplomska.sportsaway.feature.favourite.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewState

class FetchUserDataUseCase(
  private val firebaseRepository: AuthRepository
) : ErrorHandlingUseCase() {

  fun isTheUserLoggedIn() = firebaseRepository.isLogged()

  suspend fun fetchUsersFavouriteTeam(): FavouriteViewState {
    return firebaseRepository.getCurrentUser().fold(
      onSuccess = {
        if (it.favouriteTeams.isEmpty()) {
          FavouriteViewState.HasNotSelectedTeams
        } else {
          FavouriteViewState.TeamsAndMatches(it.favouriteTeams)
        }
      },
      onFailure = {
        FavouriteViewState.ShowError
      }
    )
  }
}
