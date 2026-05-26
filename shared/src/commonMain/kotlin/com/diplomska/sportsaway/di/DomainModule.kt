package com.diplomska.sportsaway.di

import com.diplomska.sportsaway.feature.authentication.login.domain.LoginUseCase
import com.diplomska.sportsaway.feature.authentication.register.domain.RegisterUseCase
import com.diplomska.sportsaway.feature.dashboard.usecase.GetCompetitionsUseCase
import com.diplomska.sportsaway.feature.dashboard.usecase.GetTrendingMatchesUseCase
import com.diplomska.sportsaway.feature.events.domain.EventDetailsUseCase
import com.diplomska.sportsaway.feature.events.domain.EventsUseCase
import com.diplomska.sportsaway.feature.favourite.domain.FetchLatestTeamInfoUseCase
import com.diplomska.sportsaway.feature.favourite.domain.FetchUserDataUseCase
import com.diplomska.sportsaway.feature.favourite.domain.TeamsUseCase
import com.diplomska.sportsaway.feature.profile.domain.GetUserDataUseCase
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

  // Favourite
  factory { FetchUserDataUseCase(get()) }
  factory { TeamsUseCase(get(), get()) }
  factory { FetchLatestTeamInfoUseCase(get()) }

  // Profile
  factory { GetUserDataUseCase(get()) }
}