package com.diplomska.sportsaway.feature.events.view.model

data class SavedCard(
  val cardId: String,
  val last4Digits: String,
  val cardType: String,
  val cardholderName: String,
  val expiryDate: String,
)
