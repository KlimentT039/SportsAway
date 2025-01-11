package com.diplomska.sportsaway.feature.profile.ui

import ErrorScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.components.MatchTile
import com.diplomska.sportsaway.common.style.compose.components.VerticalDivider
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.feature.authentication.login.view.LoginActivity
import com.diplomska.sportsaway.feature.favourite.view.AccessDeniedScreen
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import com.diplomska.sportsaway.feature.profile.model.SelectedTab
import com.diplomska.sportsaway.feature.profile.model.UserData
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProfileScreen() {
  val viewModel = koinViewModel<ProfileViewModel>()
  val uiState = viewModel.viewState.collectAsStateWithLifecycle()
  ProfileContent(uiState = uiState.value, onErrorClicked = viewModel::requestState)
}

@Composable
private fun ProfileContent(uiState: ProfileViewState, onErrorClicked: () -> Unit) {
  val context = LocalContext.current
  when (uiState) {
    is ProfileViewState.Loading -> OverlayLoader()
    is ProfileViewState.UserHasNotLoggedIn -> AccessDeniedScreen(
      message = stringResource(id = R.string.access_denied_profile),
      buttonText = stringResource(id = R.string.log_in),
      onButtonClicked = { context.startActivity(LoginActivity.createIntent(context)) }
    )

    is ProfileViewState.ShowError -> {
      ErrorScreen(
        title = stringResource(R.string.error_has_occurred),
        description = stringResource(R.string.please_try_again),
        onClick = onErrorClicked
      )
    }

    is ProfileViewState.ProfileData -> {
      UserProfile(profileData = uiState)
    }
  }
}

@Composable
private fun UserProfile(profileData: ProfileViewState.ProfileData) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundDefault)
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
        StatItem(
          count = profileData.user.visitedMatches.size,
          label = stringResource(R.string.visited)
        )
        StatItem(
          count = profileData.user.upcomingMatches.size,
          label = stringResource(R.string.upcoming)
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = profileData.user.username,
      style = MaterialTheme.typography.h6,
      color = MaterialTheme.colors.onBackground
    )
    Spacer(Modifier.height(10.dp))
    ListDivider()
    MatchContent(profileData = profileData)
  }
}

@Composable
private fun MatchContent(profileData: ProfileViewState.ProfileData) {
  var selectedTab by remember { mutableStateOf(SelectedTab.VISITED) }
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundDefault),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(vertical = 16.dp),
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      TabItem(
        text = stringResource(R.string.visited),
        isSelected = selectedTab == SelectedTab.VISITED,
        onClick = { selectedTab = SelectedTab.VISITED }
      )

      VerticalDivider(color = Color.Black)

      TabItem(
        text = stringResource(R.string.upcoming),
        isSelected = selectedTab == SelectedTab.UPCOMING,
        onClick = { selectedTab = SelectedTab.UPCOMING }
      )
    }

    ListDivider()
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      ListOfMatches(profileData, selectedTab)
    }
  }
}

@Composable
private fun ListOfMatches(profileData: ProfileViewState.ProfileData, selectedTab: SelectedTab) {
  val list =
    if (selectedTab == SelectedTab.VISITED) profileData.user.visitedMatches else profileData.user.upcomingMatches
  val noMatchesDescription =
    if (selectedTab == SelectedTab.VISITED) stringResource(R.string.no_visited_matches) else
      stringResource(R.string.no_upcoming_matches_scheduled)
  if (list.isEmpty()) {
    NoMatchContent(description = noMatchesDescription)
  } else {
    MatchesList(matches = list)
  }
}

@Composable
fun MatchesList(matches: List<Match>) {
  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
  ) {
    matches.forEach { match ->
      MatchTile(match)
    }
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
private fun NoMatchContent(description: String) {
  Box(
    Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = description,
      color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
      style = MaterialTheme.typography.body2
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
      color = if (isSelected) Color.Black else Color.Gray,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun SampleMatchProfileScreen() {
  val mockUser = UserData(
    username = "JohnDoe",
    visitedMatches = listOf(
      Match(
        homeTeam = Team(name = "Team A"),
        awayTeam = Team(name = "Team B"),
        venue = "Stadium 1",
        date = "10 Dec 2024"
      )
    ),
    upcomingMatches = listOf(
      Match(
        homeTeam = Team(name = "Team C"),
        awayTeam = Team(name = "Team D"),
        venue = "Stadium 2",
        date = "10 Dec 2024"
      )
    )
  )
  UserProfile(
    profileData = ProfileViewState.ProfileData(user = mockUser)
  )
}
