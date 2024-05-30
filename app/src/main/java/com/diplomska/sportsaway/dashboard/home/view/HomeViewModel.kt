package com.diplomska.sportsaway.dashboard.home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.dashboard.usecase.GetTrendingMatchesUseCase
import com.diplomska.sportsaway.shared.errorhandling.fold
import com.diplomska.sportsaway.events.model.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(private val getTrendingMatchesUseCase: GetTrendingMatchesUseCase) :
ViewModel() {

  private val _nextMatchesState = MutableStateFlow<ViewState>(ViewState.Loading)
  val nextMatchesState = _nextMatchesState.asStateFlow()

  init {
    fetchTrendingEvents()
    //getTrendingMatchesUseCase.getTeams()
  }

  fun onMatchClick(match: Match) {
    viewModelScope.launch {

    }
  }

  private fun fillFirebase(){

  }

  private fun fetchTrendingEvents() = viewModelScope.launch {
    getTrendingMatchesUseCase.invoke(15).fold(
      onFailure = {
        _nextMatchesState.update {
          getHomeData().copy(isError = true)
        }
      },
      onSuccess = { events ->
        _nextMatchesState.update {
          getHomeData().copy(listOfNextMatches = events)
        }
      }
    )
  }

  private fun getHomeData() = _nextMatchesState.value as? ViewState.HomeData ?: ViewState.HomeData()
}

sealed interface ViewState {
  data object Loading : ViewState

  data class HomeData(
    val listOfNextMatches: List<Match> = emptyList(),
    val isError: Boolean = false
  ) : ViewState
}