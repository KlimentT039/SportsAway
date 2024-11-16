package com.diplomska.sportsaway.feature.events.domain

import com.diplomska.core.errorhandling.ErrorHandlingUseCase
import com.diplomska.sportsaway.common.shared.errorhandling.BaseError
import com.diplomska.sportsaway.common.shared.errorhandling.Either
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.toMatch
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.feature.events.view.model.getPic
import com.diplomska.sportsaway.feature.events.view.model.toStadiumPics

class EventDetailsUseCase(private val sportsEventsRepository: SportsEventsRepository) :
  ErrorHandlingUseCase() {

  suspend operator fun invoke(id: Int): Either<BaseError, Match> = lift {
    val match = sportsEventsRepository.getMatchById(id).toMatch()
    val venue = sportsEventsRepository.getTeamById(match.homeTeam.id).venue
    val venuePics = getStadiumPic(venue)
    match.copy(venue = venue, venueImage = venuePics)
  }

  private suspend fun getStadiumPic(venue: String?): String? {
    return if (venue != null) {
      try {
        sportsEventsRepository.getStadiumPic(venue).venues.first().toStadiumPics().getPic()
      } catch (exception: Exception) {
        println(exception.toString())
        null
      }
    } else {
      null
    }
  }
}
