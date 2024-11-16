package com.diplomska.sportsaway.feature.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.feature.authentication.login.view.LoginActivity
import com.diplomska.sportsaway.feature.favourite.view.AccessDeniedScreen
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen() {
  val viewModel = koinViewModel<ProfileViewModel>()
  val uiState = viewModel.viewState.collectAsStateWithLifecycle()
  ProfileContent(uiState = uiState.value)
}

@Composable
fun ProfileContent(uiState: ProfileViewState) {
  val context = LocalContext.current
  when (uiState) {
    is ProfileViewState.Loading -> OverlayLoader()
    is ProfileViewState.UserHasNotLoggedIn -> AccessDeniedScreen(
      message = stringResource(id = R.string.access_denied_profile),
      buttonText = stringResource(id = R.string.log_in),
      onButtonClicked = { context.startActivity(LoginActivity.createIntent(context)) }
    )

    is ProfileViewState.ProfileData -> {
      UserProfile()
    }
  }
}

@Composable
fun UserProfile() {
}