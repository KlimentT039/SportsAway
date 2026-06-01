package com.diplomska.sportsaway.feature.events.view.order.dialogs.billingaddress

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BillingAddressUiState(
  val fullName: String = "",
  val addressLine1: String = "",
  val addressLine2: String = "",
  val city: String = "",
  val country: String = "",
  val zipCode: String = "",
  val errors: Map<String, String> = emptyMap()
)

class BillingAddressViewModel : ViewModel() {

  private val _state = MutableStateFlow(BillingAddressUiState())
  val state = _state.asStateFlow()

  fun onFullNameChange(value: String) = _state.update { it.copy(fullName = value) }
  fun onAddressLine1Change(value: String) = _state.update { it.copy(addressLine1 = value) }
  fun onAddressLine2Change(value: String) = _state.update { it.copy(addressLine2 = value) }
  fun onCityChange(value: String) = _state.update { it.copy(city = value) }
  fun onCountryChange(value: String) = _state.update { it.copy(country = value) }
  fun onZipCodeChange(value: String) = _state.update { it.copy(zipCode = value) }

  fun validateFields(): Boolean {
    val current = _state.value
    val validationErrors = mutableMapOf<String, String>()

    if (current.fullName.isBlank()) validationErrors["fullName"] = "Full name is required."
    if (current.addressLine1.isBlank()) validationErrors["addressLine1"] = "Address Line 1 is required."
    if (current.city.isBlank()) validationErrors["city"] = "City is required."
    if (current.country.isBlank()) validationErrors["country"] = "Country is required."
    if (current.zipCode.isBlank() || !current.zipCode.matches(Regex("\\d{5}")))
      validationErrors["zipCode"] = "Enter a valid 4-digit ZIP code."

    _state.update { it.copy(errors = validationErrors) }
    return validationErrors.isEmpty()
  }
}
