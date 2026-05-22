package com.diplomska.sportsaway.di

import com.diplomska.sportsaway.feature.authentication.login.domain.LoginUseCase
import com.diplomska.sportsaway.feature.authentication.register.domain.RegisterUseCase
import com.diplomska.sportsaway.feature.dashboard.usecase.GetCompetitionsUseCase
import com.diplomska.sportsaway.feature.dashboard.usecase.GetTrendingMatchesUseCase
import com.diplomska.sportsaway.feature.events.domain.EventDetailsUseCase
import com.diplomska.sportsaway.feature.events.domain.EventsUseCase
import org.koin.dsl.module

val domainModule = module {
  // Dashboard
  single { GetTrendingMatchesUseCase(get()) }
  single { GetCompetitionsUseCase(get()) }

  // Events
  factory { EventsUseCase(get()) }
  factory { EventDetailsUseCase(get()) }

  // Authentication
  single { LoginUseCase(get()) }
  single { RegisterUseCase(get()) }
}