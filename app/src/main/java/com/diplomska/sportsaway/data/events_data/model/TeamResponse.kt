package com.diplomska.sportsaway.data.events_data.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize
import net.bytebuddy.matcher.StringMatcher

@JsonIgnoreProperties(ignoreUnknown = true)
data class TeamResponse(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("shortName") val shortName: String,
  @JsonProperty("tla") val tla: String,
  @JsonProperty("crest") val crest: String?,
  @JsonProperty("venue") val venue: String?,
  @JsonProperty("area") val area: Area?,
  @JsonProperty("squad") val squad: List<Player>?
)

data class Area(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("code") val code: String,
  @JsonProperty("flag") val flag: String
)

@Parcelize
data class Player(
  @JsonProperty("name") val name: String,
  @JsonProperty("position") val position: String,
  @JsonProperty("dateOfBirth") val dateOfBirth: String,
  @JsonProperty("nationality") val nationality: String
) : Parcelable