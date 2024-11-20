package com.diplomska.sportsaway.feature.events.view.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.feature.events.view.model.BillingAddress
import com.diplomska.sportsaway.feature.events.view.model.OrderBundle
import com.diplomska.sportsaway.feature.events.view.model.SavedCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface OrderTicketsState {

  data object Loading : OrderTicketsState

  data class OrderTicketsData(
    val match: Match,
    val ticket: Ticket,
    val numberOfTickets: Int = 2,
    val showAddCard: Boolean = false,
    val cardData: SavedCard? = null,
    val showBillingAddress: Boolean = false,
    val showTicketWindow: Boolean = false,
    val billingAddress: BillingAddress? = null,
    val total: Int
  ) : OrderTicketsState
}

class OrderTicketsViewModel : ViewModel() {

  private val _state = MutableStateFlow<OrderTicketsState>(OrderTicketsState.Loading)
  val state = _state.asStateFlow()

  fun initData(orderBundle: OrderBundle) {
    _state.update {
      OrderTicketsState.OrderTicketsData(
        match = orderBundle.match,
        ticket = orderBundle.selectedTicket,
        total = 2 * orderBundle.selectedTicket.price
      )
    }
  }

  fun onAddCardClicked() = viewModelScope.launch {
    runWithViewStateData { state ->
      _state.update { state.copy(showAddCard = true) }
    }
  }

  fun onAddBillingAddressClicked() = viewModelScope.launch {
    runWithViewStateData { state ->
      _state.update { state.copy(showBillingAddress = true) }
    }
  }

  fun onEditNumOfTickets() = viewModelScope.launch {
    runWithViewStateData { state ->
      _state.update { state.copy(showTicketWindow = true) }
    }
  }

  private inline fun runWithViewStateData(block: (OrderTicketsState.OrderTicketsData) -> Unit) {
    val viewStateData = _state.value as? OrderTicketsState.OrderTicketsData ?: return
    block(viewStateData)
  }

}