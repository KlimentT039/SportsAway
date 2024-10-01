package com.diplomska.sportsaway.feature.events.view.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.feature.events.domain.EventsUseCase
import com.diplomska.sportsaway.feature.events.view.model.GroupedMatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

internal class EventsOverviewViewModel(
  competitionId: Int?,
  val getEventsUseCase: EventsUseCase
) :
  ViewModel() {

  private val _viewState = MutableStateFlow<EventsViewState>(EventsViewState.Loading)
  val viewState = _viewState.asStateFlow()

  private val _searchQuery = MutableStateFlow<String>("")
  val searchQuery = _searchQuery.asStateFlow()

  init {
    fetchNextMatches(competitionId)
  }

  fun onSearchQuery(query: String) {
    _searchQuery.update { query }
    val groupedMatches = getEventsState().groupedMatches
    if (query.length < 3) return
    _viewState.update { EventsViewState.Loading }
    val searchResult = getEventsUseCase.searchQuery(query, groupedMatches)
    if (searchResult.isEmpty()) {
      _viewState.update { EventsViewState.EmptySearchResult }
    } else {
      _viewState.update { getEventsState().copy(groupedMatches = searchResult) }
    }
  }

  fun fetchNextMatches(competitionId: Int?) = viewModelScope.launch {
    getEventsUseCase(competitionId).fold(
      onFailure = {
        _viewState.update { EventsViewState.Error }
      },
      onSuccess = { events ->
        _viewState.update { getEventsState().copy(groupedMatches = events) }
      }
    )
  }

  private fun getEventsState() =
    _viewState.value as? EventsViewState.EventData ?: EventsViewState.EventData()

}

sealed interface EventsViewState {
  data object Loading : EventsViewState

  data class EventData(
    val groupedMatches: List<GroupedMatch> = emptyList(),
  ) : EventsViewState

  data object EmptySearchResult : EventsViewState

  data object Error : EventsViewState
}