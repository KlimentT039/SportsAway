package com.diplomska.sportsaway.di

import com.diplomska.sportsaway.feature.authentication.di.authenticationModule
import com.diplomska.sportsaway.feature.dashboard.di.dashboardModule
import com.diplomska.sportsaway.feature.events.di.eventModule
import com.diplomska.sportsaway.feature.favourite.di.favouriteModule
import com.diplomska.sportsaway.feature.profile.di.profileModule
import com.diplomska.sportsaway.shared.currentPlatform
import org.koin.core.context.startKoin

fun doInitKoin() {
  startKoin {
    modules(
      dataModule,
      domainModule,
      platformModule(),
      dashboardModule,
      eventModule,
      profileModule,
      favouriteModule,
      authenticationModule
    )
  }
}

fun platformGreeting(): String = "Hello from KMP on ${currentPlatform().name}"
