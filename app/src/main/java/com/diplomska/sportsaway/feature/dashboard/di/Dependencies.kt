package com.diplomska.sportsaway.feature.dashboard.di

import com.diplomska.sportsaway.feature.dashboard.home.view.HomeViewModel
import com.diplomska.sportsaway.feature.dashboard.usecase.GetCompetitionsUseCase
import com.diplomska.sportsaway.feature.dashboard.usecase.GetTrendingMatchesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
  //Single
  single { GetTrendingMatchesUseCase(get()) }
  single { GetCompetitionsUseCase(get()) }

  //ViewModel
  viewModel { HomeViewModel(get(), get()) }

}