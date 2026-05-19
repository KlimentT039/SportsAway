package com.diplomska.sportsaway.data.events_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompetitionListResponse(
  @SerialName("competitions") val competitions: List<CompetitionResponse>
)

@Serializable
data class CompetitionResponse(
  @SerialName("id") val id: Int,
  @SerialName("name") val name: String,
  @SerialName("code") val code: String,
  @SerialName("type") val type: String,
  @SerialName("emblem") val emblem: String? = null
)

val listOfCompetitionIds = listOf(2001, 2021, 2014, 2019, 2012, 2015)
