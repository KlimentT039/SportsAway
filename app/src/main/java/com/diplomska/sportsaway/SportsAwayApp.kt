package com.diplomska.sportsaway

import android.app.Application
import com.diplomska.sportsaway.common.shared.di.sharedModule
import com.diplomska.sportsaway.data.events_data.di.sportDataModule
import com.diplomska.sportsaway.feature.authentication.di.authenticationModule
import com.diplomska.sportsaway.feature.dashboard.di.dashboardModule
import com.diplomska.sportsaway.feature.events.di.eventModule
import com.diplomska.sportsaway.feature.favourite.di.favouriteModule
import com.diplomska.sportsaway.feature.profile.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SportsAwayApp : Application() {

  override fun onCreate() {
    super.onCreate()
    initKoin()
  }

  private fun initKoin(){
    startKoin {
      androidContext(this@SportsAwayApp)
      modules(
        sharedModule,
        sportDataModule,
        dashboardModule,
        eventModule,
        profileModule,
        favouriteModule,
        authenticationModule
      )
    }
  }

}