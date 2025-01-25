package com.diplomska.sportsaway.feature.events.view.order.dialogs.carddialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddCardViewModel : ViewModel() {
  var cardHolderName by mutableStateOf("")
  var cardNumber by mutableStateOf("")
  var expirationDate by mutableStateOf("")
  var cvv by mutableStateOf("")

  val errors = mutableStateOf(mapOf<String, String>())

  fun validateFields(): Boolean {
    val validationErrors = mutableMapOf<String, String>()

    if (cardHolderName.isBlank()) validationErrors["cardHolderName"] = "Cardholder name is required."
    if (cardNumber.isBlank() || cardNumber.length != 16) validationErrors["cardNumber"] = "Enter a valid 16-digit card number."
    if (expirationDate.isBlank() || !expirationDate.matches(Regex("\\d{2}/\\d{2}"))) {
      validationErrors["expirationDate"] = "Enter a valid expiration date (MM/YY)."
    }
    if (cvv.isBlank() || cvv.length != 3) validationErrors["cvv"] = "Enter a valid 3-digit CVV."

    errors.value = validationErrors
    return validationErrors.isEmpty()
  }
}
