package com.diplomska.sportsaway.dashboard.home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.dashboard.usecase.GetCompetitionsUseCase
import com.diplomska.sportsaway.dashboard.usecase.GetTrendingMatchesUseCase
import com.diplomska.sportsaway.shared.errorhandling.fold
import com.diplomska.sportsaway.shared.model.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.diplomska.sportsaway.shared.model.Competition
import com.diplomska.sportsaway.dashboard.home.view.ViewState.MatchData
import com.diplomska.sportsaway.dashboard.home.view.ViewState.CompetitionsData

internal class HomeViewModel(
  private val getTrendingMatchesUseCase: GetTrendingMatchesUseCase,
  private val getCompetitionsUseCase: GetCompetitionsUseCase
) : ViewModel() {

  private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
  val viewState = _viewState.asStateFlow()

  init {
    fetchTrendingEvents()
    fetchCompetition()
  }

  private fun fetchTrendingEvents() = viewModelScope.launch {
    getTrendingMatchesUseCase.invoke(10).fold(
      onFailure = {
        _viewState.update {
          getHomeData().copy(MatchData(isError = true))
        }
      },
      onSuccess = { events ->
        _viewState.update {
          getHomeData().copy(matchData = MatchData(listOfNextMatches = events, isError = false))
        }
      }
    )
  }

  private fun fetchCompetition() = viewModelScope.launch {
    getCompetitionsUseCase.invoke().fold(
      onFailure = {
        _viewState.update {
          getHomeData().copy(competitionsData = CompetitionsData(isError = true))
        }
      },
      onSuccess = { list ->
        _viewState.update {
          getHomeData().copy(competitionsData = CompetitionsData(listOfCompetitions = list))
        }
      }
    )
  }

  private fun getHomeData() = _viewState.value as? ViewState.HomeData ?: ViewState.HomeData()
}

sealed interface ViewState {
  data object Loading : ViewState

  data class HomeData(
    val matchData: MatchData = MatchData(),
    val competitionsData: CompetitionsData = CompetitionsData()

  ) : ViewState

  data class MatchData(
    val listOfNextMatches: List<Match> = emptyList(),
    val isError: Boolean = false
  )

  data class CompetitionsData(
    val listOfCompetitions: List<Competition> = emptyList(),
    val isError: Boolean = false
  )
}