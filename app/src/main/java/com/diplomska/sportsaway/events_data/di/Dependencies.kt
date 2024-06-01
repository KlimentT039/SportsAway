package com.diplomska.sportsaway.events_data.di

import com.diplomska.sportsaway.events_data.provider.EventsJsonProvider
import com.diplomska.sportsaway.events_data.provider.TeamJsonProvider
import com.diplomska.sportsaway.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.events_data.repository.SportsEventsRepositoryImpl
import org.koin.dsl.module
import kotlin.math.sin

val sportDataModule = module {
  single { TeamJsonProvider(get()) }
  single { EventsJsonProvider(get()) }
  single {  }
  single<SportsEventsRepository> { SportsEventsRepositoryImpl(get(), get()) }
}