package com.diplomska.sportsaway.events.di

import com.diplomska.sportsaway.events.domain.EventsUseCase
import com.diplomska.sportsaway.events.domain.MapEventsResponseToMatch
import com.diplomska.sportsaway.events.view.details.EventDetailsViewModel
import com.diplomska.sportsaway.events.view.overview.EventsOverviewViewModel
import com.diplomska.sportsaway.sports_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.sports_data.repository.SportsEventsRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
  single<SportsEventsRepository> { SportsEventsRepositoryImpl() }
  single { MapEventsResponseToMatch() }
  single { EventsUseCase(get(), get()) }

  viewModel { EventsOverviewViewModel(get()) }
  viewModel { EventDetailsViewModel() }
}