package com.diplomska.sportsaway.sports_data.provider

import com.diplomska.sportsaway.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.sports_data.model.Team
import com.diplomska.sportsaway.sports_data.model.Teams

class TeamJsonProvider(assetConfigRead: AssetConfigRead) {

  private val jsonObject: Teams by lazy {
    assetConfigRead("teams.json", Teams::class)
  }

  val teams: List<Team>
    get() = jsonObject.listOfTeam

}