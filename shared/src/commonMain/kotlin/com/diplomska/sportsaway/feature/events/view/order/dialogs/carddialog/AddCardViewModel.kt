package com.diplomska.sportsaway.feature.events.view.order.dialogs.carddialog

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddCardUiState(
  val cardHolderName: String = "",
  val cardNumber: String = "",
  val expirationDate: String = "",
  val cvv: String = "",
  val errors: Map<String, String> = emptyMap()
)

class AddCardViewModel : ViewModel() {

  private val _state = MutableStateFlow(AddCardUiState())
  val state = _state.asStateFlow()

  fun onCardHolderNameChange(value: String) = _state.update { it.copy(cardHolderName = value) }
  fun onCardNumberChange(value: String) = _state.update { it.copy(cardNumber = value) }
  fun onExpirationDateChange(value: String) = _state.update { it.copy(expirationDate = value) }
  fun onCvvChange(value: String) = _state.update { it.copy(cvv = value) }

  fun validateFields(): Boolean {
    val current = _state.value
    val validationErrors = mutableMapOf<String, String>()

    if (current.cardHolderName.isBlank()) validationErrors["cardHolderName"] = "Cardholder name is required."
    if (current.cardNumber.isBlank() || current.cardNumber.length != 16)
      validationErrors["cardNumber"] = "Enter a valid 16-digit card number."
    if (current.expirationDate.isBlank() || !current.expirationDate.matches(Regex("\\d{4}")))
      validationErrors["expirationDate"] = "Enter a valid expiration date (MM/YY)."
    if (current.cvv.isBlank() || current.cvv.length != 3)
      validationErrors["cvv"] = "Enter a valid 3-digit CVV."

    _state.update { it.copy(errors = validationErrors) }
    return validationErrors.isEmpty()
  }
}
