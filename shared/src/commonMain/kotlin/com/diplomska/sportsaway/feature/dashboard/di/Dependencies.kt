package com.diplomska.sportsaway.feature.dashboard.di

import com.diplomska.sportsaway.feature.dashboard.home.view.HomeViewModel
import org.koin.dsl.module

val dashboardModule = module {
  factory { HomeViewModel(get(), get()) }
}
