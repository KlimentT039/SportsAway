package com.diplomska.sportsaway.data.events_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamsData(
  @SerialName("teams") val teams: Map<String, TeamInfo>
)

@Serializable
data class TeamInfo(
  @SerialName("latest_news") val latestNews: List<News>? = null,
  @SerialName("injury_reports") val injuryReports: List<InjuryReport>? = null
)
