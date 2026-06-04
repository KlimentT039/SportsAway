package com.diplomska.sportsaway.feature.favourite.view.news

import ErrorScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.shared.utils.GetImage
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.components.ImageWithUrl
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextPrimary
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextSecondary
import com.diplomska.sportsaway.common.style.compose.typography
import com.diplomska.sportsaway.data.events_data.model.InjuryReport
import com.diplomska.sportsaway.data.events_data.model.News
import com.diplomska.sportsaway.data.events_data.model.Player
import com.diplomska.sportsaway.data.events_data.model.TeamInfo
import com.diplomska.sportsaway.feature.favourite.model.LatestTeamInfo
import org.koin.androidx.compose.koinViewModel

@Composable
fun LatestNewsScreen(teamId: Int, onBackClicked: () -> Unit) {
  val viewModel = koinViewModel<LatestNewsViewModel>()
  viewModel.initData(teamId)
  val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
  LatestNewsContent(
    viewState = viewState,
    onBackClicked = onBackClicked,
    onTryAgainClick = { viewModel.initData(teamId) }
  )
}

@Composable
private fun LatestNewsContent(
  viewState: LatestNewsViewState,
  onBackClicked: () -> Unit,
  onTryAgainClick: () -> Unit
) {
  when (viewState) {
    is LatestNewsViewState.Loading -> {
      OverlayLoader()
    }

    is LatestNewsViewState.Error -> {
      ErrorScreen(title = stringResource(R.string.something_went_wrong), onClick = onTryAgainClick)
    }

    is LatestNewsViewState.Content -> {
      val teamInfo = viewState.teamInfo
      val team = teamInfo.team
      Scaffold.WithTopBarOnly(
        topBar = {
          AppBar.CustomTopAppBar(
            title = stringResource(R.string.team_latest_info, team.name),
            showBackButton = false,
            onBackClick = onBackClicked,
          )
        },
        content = {
          TeamHeader(team)
          TabbedContentSection(viewState.teamInfo)
        }
      )
    }
  }
}

@Composable
private fun TeamHeader(team: Team) {
  Column(modifier = Modifier.padding(16.dp)) {
    team.country?.let { countryName ->
      Row(verticalAlignment = Alignment.CenterVertically) {
        team.flag?.let { flagUrl ->
          ImageWithUrl(
            url = flagUrl,
            size = 16
          )
          Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
          text = countryName,
          style = typography.xsRegular
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
      GetImage(team.crest, pictureSize = 60)
      Spacer(modifier = Modifier.width(16.dp))
      Text(text = team.name, style = typography.mRegular)
    }
  }
}

@Composable
private fun TabbedContentSection(teamInfo: LatestTeamInfo) {
  var selectedTab by remember { mutableStateOf(InfoTab.INJURIES) }

  Column(modifier = Modifier.fillMaxWidth()) {
    TabRowSection(selectedTab = selectedTab) {
      selectedTab = it
    }

    when (selectedTab) {
      InfoTab.NEWS -> {
        NewsContent(teamInfo.teamInfo.latestNews.orEmpty())
      }

      InfoTab.SQUAD -> {
        SquadList(teamInfo.team.squad)
      }

      InfoTab.INJURIES -> {
        InjuriesContent(teamInfo.teamInfo.injuryReports.orEmpty())
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabRowSection(
  selectedTab: InfoTab,
  onTabSelected: (InfoTab) -> Unit
) {
  val tabs = InfoTab.entries
  val selectedIndex = tabs.indexOf(selectedTab)

  TabRow(
    selectedTabIndex = selectedIndex,
    containerColor = backgroundDefault,
    contentColor = typographyTextPrimary,
    indicator = { tabPositions ->
      if (selectedIndex < tabPositions.size) {
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier
            .tabIndicatorOffset(tabPositions[selectedIndex])
            .height(2.dp),
          color = backgroundSurface
        )
      }
    },
    divider = {}
  ) {
    tabs.forEachIndexed { index, tab ->
      Tab(
        selected = selectedTab == tab,
        onClick = { onTabSelected(tab) },
        text = {
          Text(
            text = stringResource(tab.getText()),
            fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
            color = if (selectedTab == tab) typographyTextPrimary else typographyTextSecondary
          )
        }
      )
    }
  }
}


@Composable
private fun NewsContent(news: List<News>) {
  LazyColumn {
    news.forEachIndexed { index, item ->
      item {
        NewsItem(item)
        if (index != news.lastIndex) {
          ListDivider()
        }
      }
    }
  }
}

@Composable
private fun InjuriesContent(injuries: List<InjuryReport>) {
  LazyColumn {
    injuries.forEachIndexed { index, report ->
      item {
        InjuryItem(report)
        if (index == injuries.lastIndex) {
          ListDivider()
        }
      }
    }
  }
}

@Composable
fun SquadList(players: List<Player>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    players.forEachIndexed { index, player ->
      item {
        PlayerItem(player)
        if (index != players.lastIndex) {
          ListDivider()
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun LatestNewsContentPreview() {
  val previewNews = listOf(
    News(
      title = "Liverpool Secures Crucial Win Against Man City",
      date = "2025-02-10",
      summary = "Liverpool defeated Manchester City 2-1 in a thrilling encounter, boosting their Premier League title hopes.",
      link = "https://backend.liverpoolfc.com/sites/default/files/styles/lg/public/2025-04/mohamed-salah-liverpool-fc-030425_c2ea0dd8e2668472e08c287ecff92243.webp?itok=exJcmN3W&width=1680"
    ),
    News(
      title = "Mohamed Salah Extends Contract Until 2027",
      date = "2025-02-08",
      summary = "Liverpool's star forward Mohamed Salah has signed a contract extension keeping him at Anfield until 2027.",
      link = "https://backend.liverpoolfc.com/sites/default/files/styles/lg/public/2025-04/mohamed-salah-liverpool-fc-030425_c2ea0dd8e2668472e08c287ecff92243.webp?itok=exJcmN3W&width=1680"
    )
  )

  val previewInjuryReports = listOf(
    InjuryReport(
      player = "Virgil van Dijk",
      injury = "Hamstring Strain",
      expectedReturn = "2025-03-01",
      status = "Doubtful for next match"
    ),
    InjuryReport(
      player = "Trent Alexander-Arnold",
      injury = "Ankle Sprain",
      expectedReturn = "2025-02-20",
      status = "Recovering"
    )
  )
  LatestNewsContent(
    viewState = LatestNewsViewState.Content(
      teamInfo = LatestTeamInfo(
        team = Team(name = "Liverpool", country = "England"),
        teamInfo = TeamInfo(
          latestNews = previewNews,
          injuryReports = previewInjuryReports
        )
      )
    ),
    onBackClicked = {},
    onTryAgainClick = {}
  )

}