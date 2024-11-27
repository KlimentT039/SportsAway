package com.diplomska.sportsaway.feature.events.view.model

data class BillingAddress(
  val fullName: String,
  val addressLine1: String,
  val addressLine2: String?,
  val city: String,
  val postalCode: String,
  val country: String,
)

