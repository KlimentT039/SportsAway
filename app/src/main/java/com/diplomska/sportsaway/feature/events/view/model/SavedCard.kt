package com.diplomska.sportsaway.feature.events.view.model

data class SavedCard(
  val cardNumber: String,
  val cardholderName: String,
  val expiryDate: String,
)

fun SavedCard.formatCardNumber(cardNumber: String): String {
  // Remove any non-digit characters
  val cleanedCardNumber = cardNumber.filter { it.isDigit() }
  return cleanedCardNumber.mapIndexed { index, char ->
    when {
      index < cleanedCardNumber.length - 4 -> '*'
      else -> char
    }
  }.chunked(4) { it.joinToString("") }
    .joinToString(" ")
}