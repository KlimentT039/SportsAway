package com.diplomska.sportsaway.feature.events.view.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.feature.events.domain.EventDetailsUseCase
import com.diplomska.sportsaway.feature.events.view.model.OrderBundle
import com.diplomska.sportsaway.feature.events.view.model.TicketFilter
import com.diplomska.sportsaway.feature.favourite.view.addteams.ViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface EventDetailsViewState {
  data object Loading : EventDetailsViewState

  data class EventData(
    val match: Match,
    val selectedFilter: TicketFilter? = null,
    val selectedTicket: Ticket? = null,
    val availableTickets: List<Ticket>
  ) : EventDetailsViewState

  data object ShowError : EventDetailsViewState
}

sealed interface DetailsEvent {
  data class ProceedToOrderScreen(val orderBundle: OrderBundle) : DetailsEvent
}

class EventDetailsViewModel(private val eventDetailsUseCase: EventDetailsUseCase) : ViewModel() {

  private val _state = MutableStateFlow<EventDetailsViewState>(EventDetailsViewState.Loading)
  val state = _state.asStateFlow()

  private val _event = MutableSharedFlow<DetailsEvent>()
  val event = _event.asSharedFlow()

  private var initialTickets: List<Ticket> = emptyList()

  fun initData(matchId: Int) = viewModelScope.launch {
    eventDetailsUseCase(matchId).fold(
      onFailure = { _state.emit(EventDetailsViewState.ShowError) },
      onSuccess = { match ->
        _state.update { EventDetailsViewState.EventData(match, availableTickets = match.tickets) }
        initialTickets = match.tickets
      }
    )
  }

  fun onBuyButtonClicked() = viewModelScope.launch {
    runWithViewStateData {
      if (it.selectedTicket == null) return@launch
      val orderBundle = OrderBundle(match = it.match, it.selectedTicket)
      _event.emit(DetailsEvent.ProceedToOrderScreen(orderBundle))
    }
  }

  fun onTicketClicked(ticket: Ticket) {
    runWithViewStateData { viewState ->
      val selectedTicket = if (viewState.selectedTicket == ticket) null else ticket
      _state.update { viewState.copy(selectedTicket = selectedTicket) }
    }
  }

  fun onTicketFilterSelected(ticketFilter: TicketFilter) {
    runWithViewStateData { viewData ->
      val match = viewData.match

      val updatedFilter = if (viewData.selectedFilter == ticketFilter) null else ticketFilter

      val filteredTickets = when (updatedFilter) {
        TicketFilter.GENERAL -> match.tickets.filter { it.ticketType == TicketFilter.GENERAL }
        TicketFilter.VIP -> match.tickets.filter { it.ticketType == TicketFilter.VIP }
        null -> match.tickets
      }
      _state.update {
        viewData.copy(
          selectedFilter = updatedFilter,
          availableTickets = filteredTickets,
          selectedTicket = null
        )
      }
    }
  }

  private inline fun runWithViewStateData(block: (EventDetailsViewState.EventData) -> Unit) {
    val viewStateData = _state.value as? EventDetailsViewState.EventData ?: return
    block(viewStateData)
  }
}