package com.diplomska.sportsaway.feature.favourite.di

import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewModel
import com.diplomska.sportsaway.feature.favourite.view.addteams.AddFavouriteTeamsViewModel
import com.diplomska.sportsaway.feature.favourite.view.news.LatestNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
  viewModel { FavouriteViewModel(get(), get()) }
  viewModel { AddFavouriteTeamsViewModel(get()) }
  viewModel { LatestNewsViewModel(get()) }
}