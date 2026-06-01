package com.diplomska.sportsaway.feature.profile.di

import com.diplomska.sportsaway.feature.profile.ui.ProfileViewModel
import org.koin.dsl.module

val profileModule = module {
  factory { ProfileViewModel(get()) }
}
