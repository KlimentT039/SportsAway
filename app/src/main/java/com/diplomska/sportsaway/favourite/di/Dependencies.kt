package com.diplomska.sportsaway.favourite.di

import com.diplomska.sportsaway.favourite.domain.HasUserLoggedInUseCase
import com.diplomska.sportsaway.favourite.view.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
  single { HasUserLoggedInUseCase(get()) }

  viewModel { FavouriteViewModel(get()) }

}