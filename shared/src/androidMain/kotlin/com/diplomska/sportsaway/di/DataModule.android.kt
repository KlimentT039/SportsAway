package com.diplomska.sportsaway.di

import com.diplomska.sportsaway.common.shared.assetConfig.AndroidAssetReader
import com.diplomska.sportsaway.common.shared.assetConfig.AssetReader
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
  single<AssetReader> { AndroidAssetReader(get()) }
}
