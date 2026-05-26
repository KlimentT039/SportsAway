package com.diplomska.sportsaway.di

import com.diplomska.sportsaway.common.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.common.shared.assetConfig.JsonMapper
import com.diplomska.sportsaway.common.shared.network.createHttpClient
import com.diplomska.sportsaway.common.shared.network.httpClientEngine
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.data.authentication_data.repository.FirebaseAuthRepository
import com.diplomska.sportsaway.data.events_data.network.SportsApi
import com.diplomska.sportsaway.data.events_data.network.StadiumApi
import com.diplomska.sportsaway.data.events_data.provider.TeamInfoJsonProvider
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepository
import com.diplomska.sportsaway.data.events_data.repository.SportsEventsRepositoryImpl
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
  single { Json { ignoreUnknownKeys = true } }
  single { JsonMapper(get()) }
  single { AssetConfigRead(get(), get()) }
  single { createHttpClient(httpClientEngine(), get()) }
  single { SportsApi(get()) }
  single { StadiumApi(get()) }
  single { TeamInfoJsonProvider(get()) }
  single<SportsEventsRepository> { SportsEventsRepositoryImpl(get(), get(), get()) }
  single<AuthRepository> { FirebaseAuthRepository() }
}

expect fun platformModule(): Module
