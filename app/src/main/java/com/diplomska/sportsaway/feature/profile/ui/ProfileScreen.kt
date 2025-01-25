package com.diplomska.sportsaway.feature.profile.ui

import ErrorScreen
import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.style.compose.components.MatchTile
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.feature.authentication.login.view.LoginActivity
import com.diplomska.sportsaway.feature.dashboard.view.DashboardActivity
import com.diplomska.sportsaway.feature.favourite.view.AccessDeniedScreen
import com.diplomska.sportsaway.feature.profile.model.ProfileViewState
import com.diplomska.sportsaway.feature.profile.model.SelectedTab
import com.diplomska.sportsaway.feature.profile.model.UserData
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {
  val uiState by viewModel.viewState.collectAsStateWithLifecycle()
  val context = LocalContext.current

  LaunchedEffect(key1 = Unit) {
    viewModel.event.collect { value ->
      if (value is ProfileEvents.NavigateToDashboard) {
        context.startActivity(Intent(context, DashboardActivity::class.java))
      }
    }
  }

  ProfileContent(
    uiState = uiState,
    onErrorClicked = viewModel::requestState,
    onLogout = viewModel::onLogout
  )
}

@Composable
private fun ProfileContent(
  uiState: ProfileViewState,
  onErrorClicked: () -> Unit,
  onLogout: () -> Unit
) {
  val context = LocalContext.current
  when (uiState) {
    is ProfileViewState.Loading -> OverlayLoader()
    is ProfileViewState.UserHasNotLoggedIn -> AccessDeniedScreen(
      message = stringResource(id = R.string.access_denied_profile),
      buttonText = stringResource(id = R.string.log_in),
      onButtonClicked = { context.startActivity(LoginActivity.createIntent(context)) }
    )

    is ProfileViewState.ShowError -> ErrorScreen(
      title = stringResource(R.string.error_has_occurred),
      description = stringResource(R.string.please_try_again),
      onClick = onErrorClicked
    )

    is ProfileViewState.ProfileData -> UserProfile(profileData = uiState, onLogout = onLogout)
  }
}

@Composable
private fun UserProfile(profileData: ProfileViewState.ProfileData, onLogout: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF5F5F5))
      .padding(16.dp)
  ) {
    ProfileHeader(profileData.user, onLogout = onLogout)
    Spacer(modifier = Modifier.height(16.dp))
    MatchContent(profileData)
  }
}

@Composable
private fun ProfileHeader(user: UserData, onLogout: () -> Unit) {
  var isMenuExpanded by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(
            Color(0xFF014421),
            Color(0xFF016F4A)
          )
        ),
        shape = MaterialTheme.shapes.medium
      )
      .shadow(8.dp, shape = MaterialTheme.shapes.medium)
      .padding(16.dp)
  ) {
    IconButton(
      onClick = { isMenuExpanded = !isMenuExpanded },
      modifier = Modifier
        .align(Alignment.TopEnd)
        .clip(CircleShape)
        .background(Color.White)
        .border(2.dp, Color(0xFF014421), CircleShape)
        .size(40.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Menu,
        contentDescription = stringResource(R.string.menu),
        tint = Color(0xFF014421)
      )
      DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = { isMenuExpanded = false },
        modifier = Modifier
          .align(Alignment.TopEnd) // Align menu with top-right corner
          .padding(end = 8.dp, top = 8.dp) // Adjust to avoid overlap with the header
      ) {
        DropdownMenuItem(
          text = { Text(text = stringResource(R.string.logout)) },
          onClick = {
            isMenuExpanded = false
            onLogout()
          }
        )
      }
    }

    // Remaining Header Content
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 64.dp), // Adjust padding for dropdown space
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Profile Picture
      Box(
        modifier = Modifier
          .size(100.dp)
          .clip(CircleShape)
          .background(
            brush = Brush.linearGradient(
              colors = listOf(Color.White, Color(0xFF014421))
            )
          )
          .border(4.dp, Color.White, CircleShape)
          .shadow(6.dp, shape = CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Person,
          contentDescription = stringResource(R.string.profile_picture),
          tint = Color(0xFF014421),
          modifier = Modifier.size(60.dp)
        )
      }
      Spacer(modifier = Modifier.height(12.dp))

      // Username
      Text(
        text = user.username,
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          color = Color.White
        ),
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.height(16.dp))

      // Stats Section
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        StatItem(
          count = user.visitedMatches.size,
          label = stringResource(R.string.visited)
        )
        StatItem(
          count = user.upcomingMatches.size,
          label = stringResource(R.string.upcoming)
        )
      }
    }
  }
}


@Composable
private fun StatItem(count: Int, label: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = count.toString(),
      style = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
    )
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium.copy(
        color = Color.LightGray
      )
    )
  }
}


@Composable
private fun MatchContent(profileData: ProfileViewState.ProfileData) {
  var selectedTab by remember { mutableStateOf(SelectedTab.VISITED) }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White, shape = MaterialTheme.shapes.medium)
      .padding(16.dp)
  ) {
    // Tabs
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      TabItem(
        text = stringResource(R.string.visited),
        isSelected = selectedTab == SelectedTab.VISITED,
        onClick = { selectedTab = SelectedTab.VISITED }
      )
      TabItem(
        text = stringResource(R.string.upcoming),
        isSelected = selectedTab == SelectedTab.UPCOMING,
        onClick = { selectedTab = SelectedTab.UPCOMING }
      )
    }
    Spacer(modifier = Modifier.height(16.dp))

    // Matches
    ListOfMatches(profileData, selectedTab)
  }
}

@Composable
private fun TabItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
  Box(
    modifier = Modifier
      .clickable(onClick = onClick)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .background(
        color = if (isSelected) Color(0xFF014421) else Color.Transparent,
        shape = MaterialTheme.shapes.small
      )
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    Text(
      text = text,
      color = if (isSelected) Color.White else Color.Gray,
      fontWeight = FontWeight.Bold,
      style = MaterialTheme.typography.bodyLarge
    )
  }
}

@Composable
private fun ListOfMatches(profileData: ProfileViewState.ProfileData, selectedTab: SelectedTab) {
  val matches = if (selectedTab == SelectedTab.VISITED) {
    profileData.user.visitedMatches
  } else {
    profileData.user.upcomingMatches
  }

  if (matches.isEmpty()) {
    NoMatchContent(
      description = if (selectedTab == SelectedTab.VISITED) {
        stringResource(R.string.no_visited_matches)
      } else {
        stringResource(R.string.no_upcoming_matches_scheduled)
      }
    )
  } else {
    MatchesList(matches = matches)
  }
}

@Composable
fun MatchesList(matches: List<Match>) {
  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .fillMaxWidth()
  ) {
    matches.forEach { match ->
      MatchTile(match)
    }
  }
}

@Composable
private fun NoMatchContent(description: String) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(
        imageVector = Icons.Default.Person, // Replace with a "no data" icon
        contentDescription = null,
        modifier = Modifier.size(48.dp),
        tint = Color.LightGray
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = description,
        color = Color.Gray,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
      )
    }
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
    profileData = ProfileViewState.ProfileData(user = mockUser),
    onLogout = {}
  )
}
