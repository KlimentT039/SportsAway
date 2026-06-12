import Foundation

enum AppRoute: Hashable {
  case events(competitionId: Int32?, title: String)
  case eventDetails(matchId: Int32, title: String)
  case addFavouriteTeams
  case latestNews(teamId: Int32, teamName: String)
}

enum AuthRoute: Hashable {
  case register
}
