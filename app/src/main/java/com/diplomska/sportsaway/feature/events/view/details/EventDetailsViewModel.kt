package com.diplomska.sportsaway.feature.events.view.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.feature.events.domain.EventDetailsUseCase
import com.diplomska.sportsaway.feature.events.view.model.TicketFilter
import com.diplomska.sportsaway.feature.favourite.view.addteams.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface EventDetailsViewState {
  data object Loading : EventDetailsViewState

  data class EventData(val match: Match, val selectedFilter: TicketFilter? = null) :
    EventDetailsViewState

  data object ShowError : EventDetailsViewState
}

class EventDetailsViewModel(private val eventDetailsUseCase: EventDetailsUseCase) : ViewModel() {

  private val _state = MutableStateFlow<EventDetailsViewState>(EventDetailsViewState.Loading)
  val state = _state.asStateFlow()

  private var initialTickets: List<Ticket> = emptyList()

  fun initData(matchId: Int) = viewModelScope.launch {
    eventDetailsUseCase(matchId).fold(
      onFailure = { _state.emit(EventDetailsViewState.ShowError) },
      onSuccess = { match ->
        _state.update { EventDetailsViewState.EventData(match) }
        initialTickets = match.tickets
      }
    )
  }

  fun onTicketFilterSelected(ticketFilter: TicketFilter) {
    runWithViewStateData { viewData ->
      val match = viewData.match

      // Toggle the filter based on the current selection
      val updatedFilter = if (viewData.selectedFilter == ticketFilter) null else ticketFilter

      val filteredTickets = when (updatedFilter) {
        TicketFilter.GENERAL -> match.tickets.filter { it.ticketType == TicketFilter.GENERAL }
        TicketFilter.VIP -> match.tickets.filter { it.ticketType == TicketFilter.VIP }
        null -> initialTickets
      }

      // Update the state with the new filter and ticket list
      _state.update {
        viewData.copy(
          selectedFilter = updatedFilter,
          match = match.copy(tickets = filteredTickets)
        )
      }
    }
  }

  private inline fun runWithViewStateData(block: (EventDetailsViewState.EventData) -> Unit) {
    val viewStateData = _state.value as? EventDetailsViewState.EventData ?: return
    block(viewStateData)
  }
}