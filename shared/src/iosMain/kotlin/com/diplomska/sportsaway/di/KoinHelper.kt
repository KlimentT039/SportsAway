package com.diplomska.sportsaway.di

import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.feature.authentication.login.view.LoginViewModel
import com.diplomska.sportsaway.feature.authentication.register.view.RegisterViewModel
import com.diplomska.sportsaway.feature.dashboard.home.view.HomeViewModel
import com.diplomska.sportsaway.feature.events.view.details.EventDetailsViewModel
import com.diplomska.sportsaway.feature.events.view.order.OrderTicketsViewModel
import com.diplomska.sportsaway.feature.events.view.order.dialogs.billingaddress.BillingAddressViewModel
import com.diplomska.sportsaway.feature.events.view.order.dialogs.carddialog.AddCardViewModel
import com.diplomska.sportsaway.feature.events.view.overview.EventsOverviewViewModel
import com.diplomska.sportsaway.feature.favourite.view.FavouriteViewModel
import com.diplomska.sportsaway.feature.favourite.view.addteams.AddFavouriteTeamsViewModel
import com.diplomska.sportsaway.feature.favourite.view.news.LatestNewsViewModel
import com.diplomska.sportsaway.feature.profile.ui.ProfileViewModel
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform

object KoinHelper {
  private val koin: Koin get() = KoinPlatform.getKoin()

  fun authRepository(): AuthRepository = koin.get<AuthRepository>()

  fun loginViewModel(): LoginViewModel = koin.get<LoginViewModel>()
  fun registerViewModel(): RegisterViewModel = koin.get<RegisterViewModel>()
  fun homeViewModel(): HomeViewModel = koin.get<HomeViewModel>()
  fun profileViewModel(): ProfileViewModel = koin.get<ProfileViewModel>()
  fun favouriteViewModel(): FavouriteViewModel = koin.get<FavouriteViewModel>()
  fun addFavouriteTeamsViewModel(): AddFavouriteTeamsViewModel = koin.get<AddFavouriteTeamsViewModel>()
  fun eventDetailsViewModel(): EventDetailsViewModel = koin.get<EventDetailsViewModel>()
  fun orderTicketsViewModel(): OrderTicketsViewModel = koin.get<OrderTicketsViewModel>()
  fun billingAddressViewModel(): BillingAddressViewModel = koin.get<BillingAddressViewModel>()
  fun addCardViewModel(): AddCardViewModel = koin.get<AddCardViewModel>()
  fun latestNewsViewModel(): LatestNewsViewModel = koin.get<LatestNewsViewModel>()

  fun eventsOverviewViewModel(competitionId: Int?): EventsOverviewViewModel =
    koin.get<EventsOverviewViewModel> { parametersOf(competitionId) }
}
