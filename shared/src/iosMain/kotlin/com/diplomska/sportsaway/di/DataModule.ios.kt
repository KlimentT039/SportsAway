package com.diplomska.sportsaway.di

import com.diplomska.sportsaway.common.shared.assetConfig.AssetReader
import com.diplomska.sportsaway.common.shared.assetConfig.IosAssetReader
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
  single<AssetReader> { IosAssetReader() }
}
