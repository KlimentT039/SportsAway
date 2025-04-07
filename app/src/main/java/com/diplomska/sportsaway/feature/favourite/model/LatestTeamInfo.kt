package com.diplomska.sportsaway.feature.favourite.model

import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.data.events_data.model.Player
import com.diplomska.sportsaway.data.events_data.model.TeamInfo

data class LatestTeamInfo(val team: Team, val teamInfo: TeamInfo)