package com.diplomska.sportsaway.data.events_data.provider

import com.diplomska.sportsaway.common.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.data.events_data.model.TeamResponse

class TeamJsonProvider(assetConfigRead: AssetConfigRead) {

  private val jsonObject: TeamResponse by lazy {
    assetConfigRead("teams.json", TeamResponse::class)
  }

}