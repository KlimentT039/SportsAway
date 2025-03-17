package com.diplomska.sportsaway.feature.events.di

import com.diplomska.sportsaway.feature.events.view.order.dialogs.billingaddress.BillingAddressViewModel
import com.diplomska.sportsaway.feature.events.domain.EventDetailsUseCase
import com.diplomska.sportsaway.feature.events.domain.EventsUseCase
import com.diplomska.sportsaway.feature.events.view.details.EventDetailsViewModel
import com.diplomska.sportsaway.feature.events.view.order.OrderTicketsViewModel
import com.diplomska.sportsaway.feature.events.view.overview.EventsOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
  factory { EventsUseCase(get()) }
  factory { EventDetailsUseCase(get()) }

  viewModel { (competitionId: Int?) -> EventsOverviewViewModel(competitionId, get()) }
  viewModel { EventDetailsViewModel(get()) }
  viewModel { OrderTicketsViewModel(get()) }
  viewModel { BillingAddressViewModel() }
}