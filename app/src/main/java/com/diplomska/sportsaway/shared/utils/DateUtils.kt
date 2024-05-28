package com.diplomska.sportsaway.shared.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun parseDate(initialString: String): String? {
  val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
  val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)

  return try {
    val parsedDate = inputFormat.parse(initialString)
    if (parsedDate != null) {
      outputFormat.format(parsedDate)
    } else {
      null
    }
  } catch (e: Exception) {
    null
  }
}