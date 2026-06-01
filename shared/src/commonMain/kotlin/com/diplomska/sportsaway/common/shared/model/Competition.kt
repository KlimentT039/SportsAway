package com.diplomska.sportsaway.common.shared.model

import com.diplomska.sportsaway.common.shared.parcelize.Parcelable
import com.diplomska.sportsaway.common.shared.parcelize.Parcelize
import com.diplomska.sportsaway.data.events_data.model.CompetitionResponse

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
