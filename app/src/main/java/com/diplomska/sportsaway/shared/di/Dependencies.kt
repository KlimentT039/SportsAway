package com.diplomska.sportsaway.shared.di

import com.diplomska.sportsaway.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.shared.assetConfig.JsonMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val sharedModule = module {
    single(named(DEFAULT_OBJECT_MAPPER)) { jacksonObjectMapper() }
    single { JsonMapper(get(named(DEFAULT_OBJECT_MAPPER))) }
    single { AssetConfigRead(androidApplication(), get()) }
}

const val DEFAULT_OBJECT_MAPPER = "defaultObjectMapper"