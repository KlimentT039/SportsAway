package com.diplomska.sportsaway.data.events_data.model

import com.diplomska.sportsaway.common.shared.parcelize.Parcelable
import com.diplomska.sportsaway.common.shared.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamResponse(
  @SerialName("id") val id: Int,
  @SerialName("name") val name: String,
  @SerialName("shortName") val shortName: String,
  @SerialName("tla") val tla: String,
  @SerialName("crest") val crest: String? = null,
  @SerialName("venue") val venue: String? = null,
  @SerialName("area") val area: Area? = null,
  @SerialName("squad") val squad: List<Player>? = null
)

@Serializable
data class Area(
  @SerialName("id") val id: Int,
  @SerialName("name") val name: String,
  @SerialName("code") val code: String,
  @SerialName("flag") val flag: String
)

@Parcelize
@Serializable
data class Player(
  @SerialName("name") val name: String? = null,
  @SerialName("position") val position: String? = null,
  @SerialName("dateOfBirth") val dateOfBirth: String? = null,
  @SerialName("nationality") val nationality: String? = null
) : Parcelable
