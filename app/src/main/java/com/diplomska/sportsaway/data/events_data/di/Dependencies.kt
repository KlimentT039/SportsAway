package com.diplomska.sportsaway.data.events_data.di

import com.diplomska.sportsaway.common.shared.network.RestClient
import com.diplomska.sportsaway.common.shared.network.getCoreHttpBuilder
import com.diplomska.sportsaway.data.events_data.network.SportsApi
import com.diplomska.sportsaway.data.events_data.provider.EventsJsonProvider
import com.diplomska.sportsaway.data.events_data.provider.TeamJsonProvider
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepositoryImpl
import org.koin.dsl.module

val sportDataModule = module {
  single { getCoreHttpBuilder().build() }
  single { TeamJsonProvider(get()) }
  single { EventsJsonProvider(get()) }
  single { RestClient.createService(SportsApi::class.java) }
  single<SportsEventsRepository> { SportsEventsRepositoryImpl(get()) }
}