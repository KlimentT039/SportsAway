package com.diplomska.sportsaway.feature.favourite.di

import com.diplomska.sportsaway.feature.favourite.domain.HasUserLoggedInUseCase
import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
  single { HasUserLoggedInUseCase(get()) }

  viewModel { FavouriteViewModel(get()) }

}