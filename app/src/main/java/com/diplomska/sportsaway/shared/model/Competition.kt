package com.diplomska.sportsaway.shared.model

import android.os.Parcelable
import com.diplomska.sportsaway.events_data.model.CompetitionResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Competition(
  val id: Int = 0,
  val name: String = "",
  val code: String = "",
  val type: String = "",
  val emblem: String? = ""
) : Parcelable

fun CompetitionResponse.toCompetition() =
  Competition(
    id = id,
    name = name,
    code = code,
    type = type,
    emblem = emblem
  )