package com.diplomska.sportsaway.events.di

import com.diplomska.sportsaway.events.domain.EventsUseCase
import com.diplomska.sportsaway.events.view.details.EventDetailsViewModel
import com.diplomska.sportsaway.events.view.overview.EventsOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
//  single { MapEventsResponseToMatch() }
  single { EventsUseCase(get()) }

  viewModel { EventsOverviewViewModel(get()) }
  viewModel { EventDetailsViewModel() }
}