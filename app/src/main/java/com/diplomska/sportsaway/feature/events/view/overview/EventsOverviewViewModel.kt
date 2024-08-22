package com.diplomska.sportsaway.feature.events.view.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.feature.events.domain.EventsUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.common.shared.model.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class EventsOverviewViewModel(val getEventsUseCase: EventsUseCase) :
  ViewModel() {

  private val _nextMatchesState = MutableStateFlow<EventsViewState>(EventsViewState.Loading)
  val nextMatchesState = _nextMatchesState.asStateFlow()

  init {
    fetchNextMatches()
  }

  fun onSearchQuery(query: String) {
    val list = getEventsData().listOfNextMatches.sortedBy {
      it.homeTeam.name.contains(query) || it.awayTeam.name.contains(query)
    }
  }

  private fun fetchNextMatches() = viewModelScope.launch {
    getEventsUseCase().fold(
      onFailure = {
        _nextMatchesState.update { getEventsData().copy(isError = true) }
      },
      onSuccess = { events ->
        _nextMatchesState.update { getEventsData().copy(listOfNextMatches = events) }
      }
    )
  }

  private fun getEventsData() =
    _nextMatchesState.value as? EventsViewState.EventData ?: EventsViewState.EventData()

}

sealed interface EventsViewState {
  data object Loading : EventsViewState

  data class EventData(
    val listOfNextMatches: List<Match> = emptyList(),
    val isError: Boolean = false
  ) : EventsViewState
}