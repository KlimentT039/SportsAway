package com.diplomska.sportsaway.feature.favourite.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.feature.favourite.domain.HasUserLoggedInUseCase
import com.diplomska.sportsaway.feature.favourite.view.model.UserFavouriteState
import kotlinx.coroutines.flow.MutableStateFlow
import com.diplomska.sportsaway.feature.favourite.view.model.UserFavouriteState.UserHasNotLoggedIn
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteViewModel(private val hasUserLoggedInUseCase: HasUserLoggedInUseCase) : ViewModel() {

  private val _viewState = MutableStateFlow<UserFavouriteState>(UserFavouriteState.Loading)
  val viewState = _viewState.asStateFlow()

  init {
    initScreen()
  }

  private fun initScreen() = viewModelScope.launch {
    if (!hasUserLoggedInUseCase.isTheUserLoggedIn()) {
      _viewState.update { UserHasNotLoggedIn }
    } else {
      _viewState.update { UserFavouriteState.UserHasNotSelectedTeams }
    }
  }


}