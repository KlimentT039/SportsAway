package com.diplomska.sportsaway.common.shared.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

val DATE_FORMAT = "dd.MMM.yyyy"

private val dateFormat = LocalDate.Format {
  dayOfMonth()
  char('.')
  monthName(MonthNames.ENGLISH_ABBREVIATED)
  char('.')
  year()
}

private val timeFormat = LocalTime.Format {
  hour()
  char(':')
  minute()
}

fun parseDate(initialString: String): String? = try {
  dateFormat.format(
    Instant.parse(initialString)
      .toLocalDateTime(TimeZone.currentSystemDefault())
      .date
  )
} catch (e: Exception) {
  null
}

fun parseDateToTime(initialString: String): String? = try {
  timeFormat.format(
    Instant.parse(initialString)
      .toLocalDateTime(TimeZone.currentSystemDefault())
      .time
  )
} catch (e: Exception) {
  null
}
