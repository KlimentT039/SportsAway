import SwiftUI
import Shared

struct FavouritesView: View {
  @StateObject private var vm = FavouriteViewModelObservable()
  @State private var path: [AppRoute] = []
  let onLoginTapped: () -> Void

  var body: some View {
    NavigationStack(path: $path) {
      Group {
        if vm.isLoading {
          ProgressView().tint(Theme.brandGreen)
        } else if vm.isError {
          ErrorBanner(message: "Couldn't load your favourites.")
            .padding(.horizontal, 16)
        } else if vm.notLoggedIn {
          signInPrompt
        } else if vm.noTeamsSelected {
          noTeamsPrompt
        } else {
          favouritesList
        }
      }
      .frame(maxWidth: .infinity, maxHeight: .infinity)
      .background(Color(.systemGroupedBackground))
      .navigationTitle("Favourites")
      .toolbarBackground(Theme.brandGreen, for: .navigationBar)
      .toolbarBackground(.visible, for: .navigationBar)
      .toolbarColorScheme(.dark, for: .navigationBar)
      .toolbar {
        if !vm.notLoggedIn && !vm.isLoading {
          ToolbarItem(placement: .topBarTrailing) {
            Button {
              path.append(.addFavouriteTeams)
            } label: {
              Image(systemName: "plus")
                .foregroundColor(.white)
            }
          }
        }
      }
      .navigationDestination(for: AppRoute.self) { route in
        AppRouteDestination(route: route)
      }
    }
    .onAppear { vm.reload() }
  }

  private var signInPrompt: some View {
    VStack(spacing: 16) {
      Image(systemName: "heart.slash")
        .font(.system(size: 64))
        .foregroundColor(.secondary)
      Text("Sign in to follow teams")
        .font(.title3.weight(.semibold))
      Button {
        onLoginTapped()
      } label: {
        Text("Sign in")
          .font(.headline)
          .foregroundColor(.white)
          .padding(.horizontal, 32)
          .padding(.vertical, 12)
          .background(Theme.brandGreen)
          .clipShape(Capsule())
      }
    }
    .padding(24)
  }

  private var noTeamsPrompt: some View {
    VStack(spacing: 16) {
      Image(systemName: "star")
        .font(.system(size: 64))
        .foregroundColor(.secondary)
      Text("Pick your favourite teams")
        .font(.title3.weight(.semibold))
      Text("We'll surface their next matches here.")
        .font(.subheadline)
        .foregroundColor(.secondary)
        .multilineTextAlignment(.center)
      Button {
        path.append(.addFavouriteTeams)
      } label: {
        Text("Add teams")
          .font(.headline)
          .foregroundColor(.white)
          .padding(.horizontal, 32)
          .padding(.vertical, 12)
          .background(Theme.brandGreen)
          .clipShape(Capsule())
      }
    }
    .padding(24)
  }

  private var favouritesList: some View {
    ScrollView {
      LazyVStack(alignment: .leading, spacing: 16) {
        ForEach(vm.favouriteTeams, id: \.favouriteTeam.id) { fav in
          FavouriteTeamSection(team: fav, onNewsTap: {
            path.append(.latestNews(teamId: fav.favouriteTeam.id, teamName: fav.favouriteTeam.shortName))
          })
        }
      }
      .padding(.vertical, 12)
    }
  }
}

private struct FavouriteTeamSection: View {
  let team: FavouriteTeam
  let onNewsTap: () -> Void

  var body: some View {
    VStack(alignment: .leading, spacing: 8) {
      HStack(spacing: 8) {
        crestImage(url: team.favouriteTeam.crest, size: 28)
        Text(team.favouriteTeam.shortName)
          .font(.headline)
        Spacer()
        Button("News", action: onNewsTap)
          .font(.subheadline)
          .foregroundColor(Theme.brandGreen)
      }
      .padding(.horizontal, 16)

      if (team.matches as? [Match])?.isEmpty ?? true {
        Text("No upcoming matches.")
          .font(.subheadline)
          .foregroundColor(.secondary)
          .padding(.horizontal, 16)
      } else {
        VStack(spacing: 8) {
          ForEach((team.matches as? [Match]) ?? [], id: \.id) { match in
            NavigationLink(value: AppRoute.eventDetails(matchId: match.id, title: "\(match.homeTeam.shortName) vs \(match.awayTeam.shortName)")) {
              FavouriteMatchRow(match: match)
            }
            .buttonStyle(.plain)
          }
        }
        .padding(.horizontal, 16)
      }
    }
  }
}

private struct FavouriteMatchRow: View {
  let match: Match

  var body: some View {
    HStack(spacing: 12) {
      VStack(alignment: .leading, spacing: 4) {
        Text("\(match.homeTeam.shortName) vs \(match.awayTeam.shortName)")
          .font(.subheadline.weight(.semibold))
          .lineLimit(1)
        Text("\(match.date) · \(match.time)")
          .font(.caption)
          .foregroundColor(.secondary)
      }
      Spacer()
      Image(systemName: "chevron.right")
        .font(.caption)
        .foregroundColor(.secondary)
    }
    .padding(12)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 10))
  }
}
