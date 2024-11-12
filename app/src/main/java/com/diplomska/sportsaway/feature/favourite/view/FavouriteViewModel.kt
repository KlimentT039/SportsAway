package com.diplomska.sportsaway.feature.favourite.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.feature.favourite.domain.FetchUserDataUseCase
import com.diplomska.sportsaway.feature.favourite.domain.TeamsUseCase
import com.diplomska.sportsaway.feature.favourite.model.FavouriteTeam
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteViewModel(
  private val fetchUserData: FetchUserDataUseCase,
  private val teamsUseCase: TeamsUseCase
) : ViewModel() {

  private val _viewState = MutableStateFlow<FavouriteViewState>(FavouriteViewState.Loading)
  val viewState = _viewState.asStateFlow()

  private val _favouriteTeams = MutableStateFlow<List<FavouriteTeam>>(emptyList())
  val favouriteTeams = _favouriteTeams.asStateFlow()

  init {
    initScreen()
  }

  fun initScreen() = viewModelScope.launch {
    if (!fetchUserData.isTheUserLoggedIn()) {
      _viewState.update { FavouriteViewState.HasNotLoggedIn }
    } else {
      _viewState.update {
        fetchUserData.fetchUsersFavouriteTeam()
      }
      fetchMatchesForFavouriteTeams()
    }
  }

  private fun fetchMatchesForFavouriteTeams() = viewModelScope.launch {
    if (viewState.value !is FavouriteViewState.TeamsAndMatches) return@launch

    val listOfIds = (viewState.value as FavouriteViewState.TeamsAndMatches).favouriteTeams
    teamsUseCase.getFavouriteEvents(listOfIds).fold(
      onFailure = { _viewState.update { FavouriteViewState.ShowError } },
      onSuccess = { teams -> _favouriteTeams.update { teams }
      }
    )
  }
}

sealed class FavouriteViewState {
  data object Loading : FavouriteViewState()
  data object HasNotLoggedIn : FavouriteViewState()
  data object ShowError : FavouriteViewState()
  data object HasNotSelectedTeams : FavouriteViewState()

  data class TeamsAndMatches(val favouriteTeams: List<Int>) : FavouriteViewState()
}