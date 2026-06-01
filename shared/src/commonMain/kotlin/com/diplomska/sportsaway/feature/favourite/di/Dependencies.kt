package com.diplomska.sportsaway.feature.favourite.di

import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewModel
import com.diplomska.sportsaway.feature.favourite.view.addteams.AddFavouriteTeamsViewModel
import com.diplomska.sportsaway.feature.favourite.view.news.LatestNewsViewModel
import org.koin.dsl.module

val favouriteModule = module {
  factory { FavouriteViewModel(get(), get()) }
  factory { AddFavouriteTeamsViewModel(get()) }
  factory { LatestNewsViewModel(get()) }
}
