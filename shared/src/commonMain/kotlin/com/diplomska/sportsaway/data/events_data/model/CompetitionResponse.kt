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

// 2000 = FIFA World Cup (in-season summer 2026), 2001 = UCL, 2021 = Premier League,
// 2014 = La Liga, 2019 = Serie A, 2012 = UEL, 2015 = Ligue 1, 2013 = Brasileiro,
// 2018 = European Championship. Mix of summer (national) and club season so the home
// screen has data year-round.
val listOfCompetitionIds = listOf(2000, 2001, 2021, 2014, 2019, 2012, 2015, 2013, 2018)
