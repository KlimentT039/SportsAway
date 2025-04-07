package com.diplomska.sportsaway.feature.favourite.di

import com.diplomska.sportsaway.feature.favourite.domain.FetchLatestTeamInfoUseCase
import com.diplomska.sportsaway.feature.favourite.domain.TeamsUseCase
import com.diplomska.sportsaway.feature.favourite.domain.FetchUserDataUseCase
import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewModel
import com.diplomska.sportsaway.feature.favourite.view.addteams.AddFavouriteTeamsViewModel
import com.diplomska.sportsaway.feature.favourite.view.news.LatestNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
  factory { FetchUserDataUseCase(get()) }
  factory { TeamsUseCase(get(), get()) }
  factory { FetchLatestTeamInfoUseCase(get()) }

  viewModel { FavouriteViewModel(get(), get()) }
  viewModel { AddFavouriteTeamsViewModel(get()) }
  viewModel { LatestNewsViewModel(get()) }

}