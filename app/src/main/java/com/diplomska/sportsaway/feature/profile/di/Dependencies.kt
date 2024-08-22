package com.diplomska.sportsaway.feature.profile.di

import com.diplomska.sportsaway.feature.profile.ui.login.domain.LoginUseCase
import com.diplomska.sportsaway.data.profile_data.repository.AuthRepository
import com.diplomska.sportsaway.feature.profile.ui.login.view.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
  single { AuthRepository() }
  single { LoginUseCase(get()) }

  viewModel { LoginViewModel(get()) }
}