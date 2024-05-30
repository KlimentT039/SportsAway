package com.diplomska.sportsaway.sports_data.di

import com.diplomska.sportsaway.sports_data.provider.EventsJsonProvider
import com.diplomska.sportsaway.sports_data.provider.TeamJsonProvider
import com.diplomska.sportsaway.sports_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.sports_data.repository.SportsEventsRepositoryImpl
import org.koin.dsl.module
import kotlin.math.sin

val sportDataModule = module {
  single { TeamJsonProvider(get()) }
  single { EventsJsonProvider(get()) }
  single<SportsEventsRepository> { SportsEventsRepositoryImpl(get()) }
}