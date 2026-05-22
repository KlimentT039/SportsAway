package com.diplomska.sportsaway.feature.dashboard.di

import com.diplomska.sportsaway.feature.dashboard.home.view.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
  //ViewModel
  viewModel { HomeViewModel(get(), get()) }

}