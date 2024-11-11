package com.diplomska.sportsaway.feature.favourite.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.feature.favourite.domain.FetchUserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteViewModel(
  private val fetchUserData: FetchUserDataUseCase
) : ViewModel() {

  private val _viewState = MutableStateFlow<FavouriteViewState>(FavouriteViewState.Loading)
  val viewState = _viewState.asStateFlow()

  init {
    initScreen()
  }

  private fun initScreen() = viewModelScope.launch {
    if (!fetchUserData.isTheUserLoggedIn()) {
      _viewState.update { FavouriteViewState.HasNotLoggedIn }
    } else {
      _viewState.update { fetchUserData.fetchUsersFavouriteTeam() }
    }
  }

  private fun fetchMatchesForFavouriteTeams() = viewModelScope.launch {

  }
}

sealed class FavouriteViewState {
  data object Loading : FavouriteViewState()
  data object HasNotLoggedIn : FavouriteViewState()
  data object ShowError : FavouriteViewState()
  data object HasNotSelectedTeams : FavouriteViewState()

  data class FavouriteTeams(val list: List<Int>) : FavouriteViewState()
}