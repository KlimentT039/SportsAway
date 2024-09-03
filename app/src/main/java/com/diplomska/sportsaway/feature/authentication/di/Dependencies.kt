package com.diplomska.sportsaway.feature.authentication.di

import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.feature.authentication.login.domain.LoginUseCase
import com.diplomska.sportsaway.feature.authentication.login.view.LoginViewModel
import com.diplomska.sportsaway.feature.authentication.register.view.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authenticationModule = module {
  single { AuthRepository() }
  single { LoginUseCase(get()) }

  viewModel { LoginViewModel(get()) }
  viewModel { RegisterViewModel(get()) }
}