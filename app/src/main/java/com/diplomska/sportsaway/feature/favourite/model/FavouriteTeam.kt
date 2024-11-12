package com.diplomska.sportsaway.feature.favourite.model

import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Team

data class FavouriteTeam(
  val favouriteTeam: Team,
  val matches: List<Match>
)
