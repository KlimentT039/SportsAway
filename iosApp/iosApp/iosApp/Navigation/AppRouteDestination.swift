import SwiftUI

/// Single source of truth for resolving an `AppRoute` into a screen.
/// Lets multiple NavigationStacks (tabs) share the same destination table.
struct AppRouteDestination: View {
  let route: AppRoute

  var body: some View {
    switch route {
    case let .events(competitionId, title):
      EventsOverviewView(competitionId: competitionId, title: title)
    case let .eventDetails(matchId, title):
      EventDetailsView(matchId: matchId, title: title)
    case .addFavouriteTeams:
      AddFavouriteTeamsView()
    case let .latestNews(teamId, teamName):
      LatestNewsView(teamId: teamId, teamName: teamName)
    }
  }
}
