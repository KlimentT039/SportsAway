package com.diplomska.sportsaway.feature.events.view.model

import com.diplomska.sportsaway.common.shared.model.Competition
import com.diplomska.sportsaway.common.shared.model.Match

data class GroupedMatch(
  val competition: Competition,
  val matches: List<Match>
)
