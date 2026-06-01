package com.diplomska.sportsaway.feature.events.di

import com.diplomska.sportsaway.feature.events.view.details.EventDetailsViewModel
import com.diplomska.sportsaway.feature.events.view.order.OrderTicketsViewModel
import com.diplomska.sportsaway.feature.events.view.order.dialogs.billingaddress.BillingAddressViewModel
import com.diplomska.sportsaway.feature.events.view.order.dialogs.carddialog.AddCardViewModel
import com.diplomska.sportsaway.feature.events.view.overview.EventsOverviewViewModel
import org.koin.dsl.module

val eventModule = module {
  factory { params -> EventsOverviewViewModel(params.getOrNull(), get()) }
  factory { EventDetailsViewModel(get()) }
  factory { OrderTicketsViewModel(get()) }
  factory { BillingAddressViewModel() }
  factory { AddCardViewModel() }
}
