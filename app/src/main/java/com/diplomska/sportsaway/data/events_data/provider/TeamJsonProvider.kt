package com.diplomska.sportsaway.data.events_data.provider

import com.diplomska.sportsaway.common.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.data.events_data.model.Team
import com.diplomska.sportsaway.data.events_data.model.Teams

class TeamJsonProvider(assetConfigRead: AssetConfigRead) {

  private val jsonObject: Teams by lazy {
    assetConfigRead("teams.json", Teams::class)
  }

  val teams: List<Team>
    get() = jsonObject.listOfTeam

}