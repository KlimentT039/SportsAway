package com.diplomska.sportsaway.feature.dashboard.home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.common.shared.model.Competition
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.feature.dashboard.home.view.ViewState.CompetitionsData
import com.diplomska.sportsaway.feature.dashboard.home.view.ViewState.MatchData
import com.diplomska.sportsaway.feature.dashboard.usecase.GetCompetitionsUseCase
import com.diplomska.sportsaway.feature.dashboard.usecase.GetTrendingMatchesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(
  private val getTrendingMatchesUseCase: GetTrendingMatchesUseCase,
  private val getCompetitionsUseCase: GetCompetitionsUseCase
) : ViewModel() {

  private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
  val viewState = _viewState.asStateFlow()

  init {
    fetchHomeData()
  }

  private fun fetchHomeData() = viewModelScope.launch {
    val trendingResult = async { getTrendingMatchesUseCase.invoke(10) }
    val competitionsResult = async { getCompetitionsUseCase.invoke() }

    try {
      val trendingEvents = trendingResult.await()
      val competitions = competitionsResult.await()

      val matchData = trendingEvents.fold(
        onFailure = { MatchData(isError = true) },
        onSuccess = { events -> MatchData(listOfNextMatches = events, isError = false) }
      )

      val competitionsData = competitions.fold(
        onFailure = { CompetitionsData(isError = true) },
        onSuccess = { list -> CompetitionsData(listOfCompetitions = list) }
      )

      _viewState.update {
        getHomeData().copy(
          matchData = matchData,
          competitionsData = competitionsData
        )
      }
    } catch (e: Exception) {
      // Handle potential exceptions in the coroutine scope
      _viewState.update {
        getHomeData().copy(
          matchData = MatchData(isError = true),
          competitionsData = CompetitionsData(isError = true)
        )
      }
    }
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