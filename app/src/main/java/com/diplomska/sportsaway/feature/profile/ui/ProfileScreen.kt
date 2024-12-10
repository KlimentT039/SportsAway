package com.diplomska.sportsaway.feature.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.feature.authentication.login.view.LoginActivity
import com.diplomska.sportsaway.feature.favourite.view.AccessDeniedScreen
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import com.diplomska.sportsaway.feature.profile.model.UserData
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen() {
  val viewModel = koinViewModel<ProfileViewModel>()
  val uiState = viewModel.viewState.collectAsStateWithLifecycle()
  ProfileContent(uiState = uiState.value)
}

@Composable
private fun ProfileContent(uiState: ProfileViewState) {
  val context = LocalContext.current
  when (uiState) {
    is ProfileViewState.Loading -> OverlayLoader()
    is ProfileViewState.UserHasNotLoggedIn -> AccessDeniedScreen(
      message = stringResource(id = R.string.access_denied_profile),
      buttonText = stringResource(id = R.string.log_in),
      onButtonClicked = { context.startActivity(LoginActivity.createIntent(context)) }
    )

    is ProfileViewState.ShowError -> {

    }

    is ProfileViewState.ProfileData -> {

    }
  }
}

@Composable
private fun UserProfile(userData: UserData) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Top Row: Avatar, Username, and Stats
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Profile Image
      Image(
        imageVector = Icons.Default.Person,
        contentDescription = stringResource(R.string.profile_picture),
        modifier = Modifier
          .size(80.dp)
          .clip(CircleShape)
          .border(2.dp, Color.Gray, CircleShape)
      )

      // Match Stats
      Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        StatItem(count = userData.visitedMatches.size, label = stringResource(R.string.visited))
        StatItem(count = userData.upcomingMatches.size, label = stringResource(R.string.upcoming))
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Username
    Text(
      text = userData.username,
      style = MaterialTheme.typography.h6,
      color = MaterialTheme.colors.onBackground
    )
    Spacer(Modifier.height(10.dp))
    ListDivider()
  }
}

@Composable
private fun StatItem(count: Int, label: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = count.toString(),
      style = MaterialTheme.typography.h6,
      color = MaterialTheme.colors.onBackground
    )
    Text(
      text = label,
      style = MaterialTheme.typography.caption,
      color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
    )
  }
}

@Composable
private fun TabItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
  Box(
    modifier = Modifier
      .clickable(onClick = onClick)
      .padding(horizontal = 16.dp)
  ) {
    Text(
      text = text,
      color = if (isSelected) Color.White else Color.Gray,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    )
  }
}

@Preview
@Composable
private fun SampleMatchProfileScreen() {
  UserProfile(userData = UserData("Klt", emptyList(), emptyList()))
}
