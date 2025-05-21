package com.diplomska.sportsaway.feature.events.view.order.dialogs.billingaddress

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BillingAddressViewModel : ViewModel() {
  var fullName by mutableStateOf("")
  var addressLine1 by mutableStateOf("")
  var addressLine2 by mutableStateOf("")
  var city by mutableStateOf("")
  var country by mutableStateOf("")
  var zipCode by mutableStateOf("")

  val errors = mutableStateOf(mapOf<String, String>())

  fun validateFields(): Boolean {
    val validationErrors = mutableMapOf<String, String>()

    if (fullName.isBlank()) validationErrors["fullName"] = "Full name is required."
    if (addressLine1.isBlank()) validationErrors["addressLine1"] = "Address Line 1 is required."
    if (city.isBlank()) validationErrors["city"] = "City is required."
    if (country.isBlank()) validationErrors["country"] = "Country is required."
    if (zipCode.isBlank() || !zipCode.matches(Regex("\\d{5}")))
      validationErrors["zipCode"] = "Enter a valid 4-digit ZIP code."

    errors.value = validationErrors
    return validationErrors.isEmpty()
  }
}
