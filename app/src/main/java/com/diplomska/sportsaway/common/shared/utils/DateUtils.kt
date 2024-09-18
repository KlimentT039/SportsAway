package com.diplomska.sportsaway.common.shared.utils

import java.text.SimpleDateFormat
import java.util.Locale

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun parseDate(initialString: String): String? {
  return try {
    // Parse the ISO 8601 date string
    val dateTime = ZonedDateTime.parse(initialString, DateTimeFormatter.ISO_DATE_TIME)

    // Convert to desired format
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy")
    dateTime.format(outputFormatter)
  } catch (e: Exception) {
    // Handle parsing exceptions
    null
  }
}
