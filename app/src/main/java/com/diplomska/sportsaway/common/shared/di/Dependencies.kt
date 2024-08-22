package com.diplomska.sportsaway.common.shared.di

import com.diplomska.sportsaway.common.shared.assetConfig.AssetConfigRead
import com.diplomska.sportsaway.common.shared.assetConfig.JsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    single(named(DEFAULT_OBJECT_MAPPER)) { jacksonObjectMapper() }
    single { JsonMapper(get(named(DEFAULT_OBJECT_MAPPER))) }
    single { AssetConfigRead(androidApplication(), get()) }
}

const val DEFAULT_OBJECT_MAPPER = "defaultObjectMapper"