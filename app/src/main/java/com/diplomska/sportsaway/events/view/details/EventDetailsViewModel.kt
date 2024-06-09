package com.diplomska.sportsaway.events.view.details

import androidx.lifecycle.ViewModel
import com.diplomska.sportsaway.shared.model.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface EventDetailsViewState {
  data object Loading : EventDetailsViewState

  data class EventData(val match: Match = Match()) : EventDetailsViewState
}

class EventDetailsViewModel : ViewModel() {

  private val _state = MutableStateFlow<EventDetailsViewState>(EventDetailsViewState.Loading)
  val state = _state.asStateFlow()

  fun initData(match: Match?) {
    _state.update { EventDetailsViewState.EventData(match ?: Match()) }
  }


}