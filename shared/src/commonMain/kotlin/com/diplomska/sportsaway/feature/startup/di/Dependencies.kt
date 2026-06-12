package com.diplomska.sportsaway.feature.startup.di

import com.diplomska.sportsaway.feature.startup.StartupViewModel
import org.koin.dsl.module

val startupModule = module {
  factory { StartupViewModel(get()) }
}
