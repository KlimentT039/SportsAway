package com.diplomska.sportsaway.di

import com.diplomska.sportsaway.common.shared.assetConfig.AssetReader
import com.diplomska.sportsaway.common.shared.assetConfig.IosAssetReader
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.data.authentication_data.repository.StubAuthRepository
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
  single<AssetReader> { IosAssetReader() }
  single<AuthRepository> { StubAuthRepository() }
}
