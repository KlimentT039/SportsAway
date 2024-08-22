package com.diplomska.sportsaway.feature.profile.ui

import androidx.lifecycle.ViewModel
import com.diplomska.sportsaway.data.authentication_data.repository.AuthRepository
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState.Loading
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private val _viewState = MutableStateFlow<ProfileViewState>(Loading)
  val viewState = _viewState.asStateFlow()




}