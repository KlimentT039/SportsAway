import SwiftUI
import Shared

struct HomeView: View {
  @StateObject private var vm = HomeViewModelObservable()
  @State private var path: [AppRoute] = []

  var body: some View {
    NavigationStack(path: $path) {
      ScrollView {
        VStack(alignment: .leading, spacing: 24) {
          if vm.isLoading {
            HStack {
              Spacer()
              ProgressView().tint(Theme.brandGreen)
              Spacer()
            }
            .padding(.top, 80)
          } else if vm.hasError {
            ErrorBanner(message: "Couldn't load home data. Pull-to-refresh isn't wired yet — try again later.")
              .padding(.horizontal, 16)
          } else {
            trendingSection
            leaguesSection
          }
        }
        .padding(.vertical, 16)
      }
      .background(Color(.systemGroupedBackground))
      .navigationTitle("SportsAway")
      .toolbarBackground(Theme.brandGreen, for: .navigationBar)
      .toolbarBackground(.visible, for: .navigationBar)
      .toolbarColorScheme(.dark, for: .navigationBar)
      .navigationDestination(for: AppRoute.self) { route in
        AppRouteDestination(route: route)
      }
    }
  }

  private var trendingSection: some View {
    VStack(alignment: .leading, spacing: 8) {
      sectionHeader(title: "Trending events")
      ScrollView(.horizontal, showsIndicators: false) {
        HStack(spacing: 8) {
          ForEach(vm.trendingMatches, id: \.id) { match in
            Button {
              path.append(.eventDetails(matchId: match.id, title: "\(match.homeTeam.shortName) vs \(match.awayTeam.shortName)"))
            } label: {
              MatchFeaturedCard(match: match)
                .containerRelativeFrame(.horizontal, count: 10, span: 9, spacing: 8)
            }
            .buttonStyle(.plain)
          }
        }
        .padding(.horizontal, 8)
      }
    }
  }

  private var leaguesSection: some View {
    VStack(alignment: .leading, spacing: 8) {
      sectionHeader(title: "Leagues")
      ScrollView(.horizontal, showsIndicators: false) {
        HStack(spacing: 8) {
          ForEach(vm.competitions, id: \.id) { comp in
            Button {
              path.append(.events(competitionId: comp.id, title: comp.name))
            } label: {
              CompetitionTile(competition: comp)
            }
            .buttonStyle(.plain)
          }
        }
        .padding(.horizontal, 8)
      }
    }
  }

  private func sectionHeader(title: String) -> some View {
    HStack {
      Text(title)
        .font(.title2.weight(.semibold))
      Spacer()
    }
    .padding(.horizontal, 16)
  }
}

struct MatchFeaturedCard: View {
  let match: Match

  var body: some View {
    VStack(spacing: 12) {
      HStack {
        Text(match.competition.name)
          .font(.headline.weight(.bold))
          .lineLimit(1)
        Spacer()
        Text("MD \(match.matchday)")
          .font(.subheadline.weight(.semibold))
          .foregroundColor(.secondary)
      }
      HStack {
        teamColumn(name: match.homeTeam.shortName, crest: match.homeTeam.crest)
        Text("vs")
          .font(.headline.bold())
          .foregroundColor(.secondary)
          .padding(.horizontal, 16)
        teamColumn(name: match.awayTeam.shortName, crest: match.awayTeam.crest)
      }
      Text(match.homeTeam.venue)
        .font(.footnote)
        .foregroundColor(.secondary)
        .multilineTextAlignment(.center)
        .lineLimit(2)
    }
    .padding(16)
    .frame(maxWidth: .infinity)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 12))
    .shadow(color: .black.opacity(0.05), radius: 4, y: 1)
  }

  private func teamColumn(name: String, crest: String?) -> some View {
    VStack(spacing: 4) {
      crestImage(url: crest, size: 40)
      Text(name)
        .font(.subheadline.weight(.medium))
        .lineLimit(1)
        .multilineTextAlignment(.center)
    }
    .frame(maxWidth: .infinity)
  }
}

struct CompetitionTile: View {
  let competition: Competition

  var body: some View {
    VStack {
      crestImage(url: competition.emblem, size: 56)
      Text(competition.name)
        .font(.caption)
        .lineLimit(1)
    }
    .padding(12)
    .frame(width: 120, height: 120)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 12))
  }
}

struct ErrorBanner: View {
  let message: String

  var body: some View {
    HStack(alignment: .top, spacing: 12) {
      Image(systemName: "exclamationmark.triangle.fill")
        .foregroundColor(.orange)
      Text(message)
        .font(.subheadline)
      Spacer(minLength: 0)
    }
    .padding(12)
    .background(Color.orange.opacity(0.12))
    .clipShape(RoundedRectangle(cornerRadius: 10))
  }
}
