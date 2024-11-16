package com.diplomska.sportsaway.data.events_data.di

import com.diplomska.sportsaway.common.shared.network.RestClient
import com.diplomska.sportsaway.common.shared.network.getCoreHttpBuilder
import com.diplomska.sportsaway.data.events_data.network.SportsApi
import com.diplomska.sportsaway.data.events_data.network.StadiumApi
import com.diplomska.sportsaway.data.events_data.provider.EventsJsonProvider
import com.diplomska.sportsaway.data.events_data.provider.TeamJsonProvider
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepositoryImpl
import org.koin.dsl.module

const val STADIUM_URL = "https://www.thesportsdb.com/api/v1/json/3/"

val sportDataModule = module {
  single { getCoreHttpBuilder().build() }
  single { TeamJsonProvider(get()) }
  single { EventsJsonProvider(get()) }
  single { RestClient.createService(SportsApi::class.java) }
  single { RestClient.createService(StadiumApi::class.java, STADIUM_URL) }
  single<SportsEventsRepository> { SportsEventsRepositoryImpl(get(), get()) }
}