package com.diplomska.sportsaway

import android.app.Application
import com.diplomska.sportsaway.dashboard.di.dashboardModule
import com.diplomska.sportsaway.events.di.eventModule
import com.diplomska.sportsaway.favourite.di.favouriteModule
import com.diplomska.sportsaway.profile.di.profileModule
import com.diplomska.sportsaway.shared.di.sharedModule
import com.diplomska.sportsaway.events_data.di.sportDataModule
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
        favouriteModule
      )
    }
  }

}