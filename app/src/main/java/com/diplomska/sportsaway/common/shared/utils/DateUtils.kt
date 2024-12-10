package com.diplomska.sportsaway.common.shared.utils

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun parseDate(initialString: String): String? {
  return try {
    // Parse the ISO 8601 date string
    val dateTime = ZonedDateTime.parse(initialString, DateTimeFormatter.ISO_DATE_TIME)

    // Convert to system default time zone
    val zonedDateTime = dateTime.withZoneSameInstant(ZoneId.systemDefault())

    // Convert to desired date format
    val outputFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    zonedDateTime.format(outputFormatter)
  } catch (e: Exception) {
    // Handle parsing exceptions
    null
  }
}

fun parseDateToTime(initialString: String): String? {
  return try {
    // Parse the ISO 8601 date string
    val dateTime = ZonedDateTime.parse(initialString, DateTimeFormatter.ISO_DATE_TIME)

    // Convert to system default time zone
    val zonedDateTime = dateTime.withZoneSameInstant(ZoneId.systemDefault())

    // Convert to 24-hour time format
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
    zonedDateTime.format(outputFormatter)
  } catch (e: Exception) {
    // Handle parsing exceptions
    null
  }
}

val DATE_FORMAT = "dd.MMM.yyyy"