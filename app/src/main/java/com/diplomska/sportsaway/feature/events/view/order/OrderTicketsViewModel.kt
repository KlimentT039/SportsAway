package com.diplomska.sportsaway.feature.events.view.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.data.authentication_data.repository.FirebaseRepository
import com.diplomska.sportsaway.feature.events.view.model.BillingAddress
import com.diplomska.sportsaway.feature.events.view.model.OrderBundle
import com.diplomska.sportsaway.feature.events.view.model.SavedCard
import com.google.firebase.firestore.firestoreSettings
import kotlinx.coroutines.delay
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
    val total: Int,
    val isButtonEnabled: Boolean = false,
    val showOrderIsSuccessful: Boolean = false,
  ) : OrderTicketsState
}

class OrderTicketsViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel() {

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

  fun onAddBillingAddressClicked() = runWithViewStateData { state ->
    _state.update { state.copy(showBillingAddress = true) }
  }

  fun onEditNumOfTickets() = runWithViewStateData { state ->
    _state.update { state.copy(showTicketWindow = true) }
  }

  fun onSelectNumOfTickets(numberOfTickets: Int) = runWithViewStateData { state ->
    _state.update {
      state.copy(
        numberOfTickets = numberOfTickets,
        total = numberOfTickets * state.ticket.price
      )
    }
  }

  fun onDismissClicked() = runWithViewStateData { state ->
    _state.update {
      state.copy(
        showBillingAddress = false,
        showAddCard = false,
        showTicketWindow = false
      )
    }
  }

  fun onSaveCardClicked(cardName: String, cardNum: String, expDate: String) {
    val savedCard = SavedCard(cardholderName = cardName, cardNumber = cardNum, expiryDate = expDate)
    runWithViewStateData { state ->
      _state.update {
        val updatedState = state.copy(cardData = savedCard)
        updatedState.copy(isButtonEnabled = updatedState.isButtonEnabled())
      }
    }
  }

  fun onSaveBillingAddress(
    fullName: String,
    addressLine1: String,
    addressLine2: String,
    city: String,
    zipCode: String,
    country: String
  ) {
    val billingAddress = BillingAddress(
      fullName = fullName,
      addressLine1 = addressLine1,
      addressLine2 = addressLine2,
      city = city,
      postalCode = zipCode,
      country = country,
    )
    runWithViewStateData { state ->
      _state.update {
        val updatedState = state.copy(billingAddress = billingAddress)
        updatedState.copy(isButtonEnabled = updatedState.isButtonEnabled())
      }
    }
  }

  fun slideOrderComplete() = viewModelScope.launch {
    runWithViewStateData { state ->
      _state.update { OrderTicketsState.Loading }
      delay(500)
      firebaseRepository.addMatchToUserDatabase(state.match)
      _state.update { state.copy(showOrderIsSuccessful = true) }
    }
  }

  private fun OrderTicketsState.OrderTicketsData.isButtonEnabled(): Boolean =
    this.billingAddress != null && this.cardData != null

  private inline fun runWithViewStateData(block: (OrderTicketsState.OrderTicketsData) -> Unit) {
    val viewStateData = _state.value as? OrderTicketsState.OrderTicketsData ?: return
    block(viewStateData)
  }
}