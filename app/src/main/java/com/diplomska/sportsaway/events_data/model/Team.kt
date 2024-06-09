package com.diplomska.sportsaway.events_data.model

import com.fasterxml.jackson.annotation.JsonProperty


data class Teams(
  @JsonProperty("teams")
  val listOfTeam: List<Team>
)

data class Team(
  val name: String = "",
  val city: String = "",
  val stadium: String = "",
  val domesticLeague: String = "",
)