package com.diplomska.sportsaway.profile.di

import com.diplomska.sportsaway.profile.ui.login.domain.LoginUseCase
import com.diplomska.sportsaway.profile_data.repository.AuthRepository
import com.diplomska.sportsaway.profile.ui.login.view.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
  single { AuthRepository() }
  single { LoginUseCase(get()) }

  viewModel { LoginViewModel(get()) }
}