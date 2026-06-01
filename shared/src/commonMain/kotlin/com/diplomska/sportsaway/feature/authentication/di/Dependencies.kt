package com.diplomska.sportsaway.feature.authentication.di

import com.diplomska.sportsaway.feature.authentication.login.view.LoginViewModel
import com.diplomska.sportsaway.feature.authentication.register.view.RegisterViewModel
import org.koin.dsl.module

val authenticationModule = module {
  factory { LoginViewModel(get()) }
  factory { RegisterViewModel(get()) }
}
