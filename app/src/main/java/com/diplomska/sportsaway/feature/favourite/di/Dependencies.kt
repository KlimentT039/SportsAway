package com.diplomska.sportsaway.feature.favourite.di

import com.diplomska.sportsaway.feature.favourite.domain.TeamsUseCase
import com.diplomska.sportsaway.feature.favourite.domain.FetchUserDataUseCase
import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewModel
import com.diplomska.sportsaway.feature.favourite.view.addteams.AddFavouriteTeamsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
  single { FetchUserDataUseCase(get()) }
  single { TeamsUseCase(get(), get()) }

  viewModel { FavouriteViewModel(get()) }
  viewModel { AddFavouriteTeamsViewModel(get()) }

}