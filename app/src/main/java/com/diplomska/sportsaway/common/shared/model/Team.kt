package com.diplomska.sportsaway.common.shared.model

import android.os.Parcelable
import com.diplomska.sportsaway.data.events_data.model.Player
import com.diplomska.sportsaway.data.events_data.model.TeamResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
  val id: Int = 0,
  val name: String = "",
  val shortName: String = "",
  val tla: String = "",
  val crest: String? = "",
  val venue: String = "",
  val country: String? = null,
  val isFavourite: Boolean = false,
  val squad: List<Player> = emptyList(),
  val flag: String? = null
) : Parcelable

fun TeamResponse.toTeam() = Team(
  id = id,
  name = name,
  shortName = shortName,
  tla = tla,
  crest = crest,
  country = area?.name,
  venue = venue ?: "",
  squad = squad.orEmpty(),
  flag = area?.flag
)