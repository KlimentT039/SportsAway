package com.diplomska.sportsaway.common.shared.di

import com.diplomska.sportsaway.common.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.common.shared.assetConfig.JsonMapper
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sharedModule = module {
  single { Json { ignoreUnknownKeys = true } }
  single { JsonMapper(get()) }
  single { AssetConfigRead(androidApplication(), get()) }
}
