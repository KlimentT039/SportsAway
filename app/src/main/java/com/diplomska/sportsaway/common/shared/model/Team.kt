package com.diplomska.sportsaway.common.shared.model

import android.os.Parcelable
import com.diplomska.sportsaway.data.events_data.model.TeamResponse
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Team(
  val id: Int = 0,
  val name: String = "",
  val shortName: String = "",
  val tla: String = "",
  val crest: String? = "",
  val venue: String = "",
) : Parcelable

fun TeamResponse.toTeam() = Team(
  id = id,
  name = name,
  shortName = shortName,
  tla  = tla,
  crest = crest,
  venue = venue ?: "",
)