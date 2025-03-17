package com.diplomska.sportsaway.data.events_data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TeamsData(
  @JsonProperty("teams")
  val teams: Map<String, TeamInfo>
)

data class TeamInfo(
  @JsonProperty("latest_news")
  val latestNews: List<News>?,
  @JsonProperty("injury_reports")
  val injuryReports: List<InjuryReport>?
)
