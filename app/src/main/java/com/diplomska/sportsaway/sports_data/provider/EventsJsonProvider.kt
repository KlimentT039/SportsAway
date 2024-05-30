package com.diplomska.sportsaway.sports_data.provider

import com.diplomska.sportsaway.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.sports_data.model.Event
import com.diplomska.sportsaway.sports_data.model.Events

class EventsJsonProvider(private val assetConfigRead: AssetConfigRead) {
  private val jsonObject: Events by lazy {
    assetConfigRead("events.json", Events::class)
  }

  val events: List<Event>
    get() = jsonObject.events
}