package com.diplomska.sportsaway.dashboard.di

import com.diplomska.sportsaway.dashboard.home.view.HomeViewModel
import com.diplomska.sportsaway.dashboard.usecase.GetCompetitionsUseCase
import com.diplomska.sportsaway.dashboard.usecase.GetTrendingMatchesUseCase
import com.diplomska.sportsaway.shared.network.getCoreHttpBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
  //Single
  single { GetTrendingMatchesUseCase(get()) }
  single { GetCompetitionsUseCase(get()) }

  //ViewModel
  viewModel { HomeViewModel(get(), get()) }

}