package com.diplomska.sportsaway.feature.events.view.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.feature.events.domain.EventDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface EventDetailsViewState {
  data object Loading : EventDetailsViewState

  data class EventData(val match: Match) : EventDetailsViewState

  data object ShowError : EventDetailsViewState
}

class EventDetailsViewModel(private val eventDetailsUseCase: EventDetailsUseCase) : ViewModel() {

  private val _state = MutableStateFlow<EventDetailsViewState>(EventDetailsViewState.Loading)
  val state = _state.asStateFlow()

  fun initData(matchId: Int) = viewModelScope.launch {
    eventDetailsUseCase(matchId).fold(
      onFailure = { _state.emit(EventDetailsViewState.ShowError) },
      onSuccess = { match -> _state.update { EventDetailsViewState.EventData(match) } }
    )
  }
}