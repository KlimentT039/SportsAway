package com.diplomska.sportsaway.events_data.di

import com.diplomska.sportsaway.events_data.network.SportsApi
import com.diplomska.sportsaway.events_data.provider.EventsJsonProvider
import com.diplomska.sportsaway.events_data.provider.TeamJsonProvider
import com.diplomska.sportsaway.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.events_data.repository.SportsEventsRepositoryImpl
import com.diplomska.sportsaway.shared.network.RestClient
import com.diplomska.sportsaway.shared.network.getCoreHttpBuilder
import org.koin.dsl.module
import kotlin.math.sin

val sportDataModule = module {
  single { getCoreHttpBuilder().build() }
  single { TeamJsonProvider(get()) }
  single { EventsJsonProvider(get()) }
  single { RestClient.createService(SportsApi::class.java) }
  single<SportsEventsRepository> { SportsEventsRepositoryImpl(get(), get(), get()) }
}