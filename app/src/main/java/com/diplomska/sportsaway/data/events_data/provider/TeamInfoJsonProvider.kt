package com.diplomska.sportsaway.data.events_data.provider

import com.diplomska.sportsaway.common.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.data.events_data.model.TeamsData

class TeamInfoJsonProvider(assetConfigRead: AssetConfigRead) {

  private val jsonObject: TeamsData by lazy {
    assetConfigRead("teaminfo.json", TeamsData::class)
  }

  val teamsData = jsonObject.teams

}