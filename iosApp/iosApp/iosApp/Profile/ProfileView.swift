import SwiftUI
import Shared

struct ProfileView: View {
  @StateObject private var vm = ProfileViewModelObservable()
  @State private var tab: ProfileTab = .upcoming
  let onLoginTapped: () -> Void

  enum ProfileTab: Hashable { case upcoming, visited }

  var body: some View {
    NavigationStack {
      Group {
        if vm.isLoading {
          ProgressView().tint(Theme.brandGreen)
        } else if vm.isError {
          ErrorBanner(message: "Couldn't load your profile.")
            .padding(.horizontal, 16)
        } else if vm.notLoggedIn {
          notLoggedInView
        } else {
          loggedInView
        }
      }
      .frame(maxWidth: .infinity, maxHeight: .infinity)
      .background(Color(.systemGroupedBackground))
      .navigationTitle("Profile")
      .toolbarBackground(Theme.brandGreen, for: .navigationBar)
      .toolbarBackground(.visible, for: .navigationBar)
      .toolbarColorScheme(.dark, for: .navigationBar)
      .toolbar {
        if !vm.notLoggedIn && !vm.isLoading {
          ToolbarItem(placement: .topBarTrailing) {
            Button {
              vm.logout()
            } label: {
              Image(systemName: "rectangle.portrait.and.arrow.right")
                .foregroundColor(.white)
            }
          }
        }
      }
    }
    .onAppear { vm.reload() }
  }

  private var notLoggedInView: some View {
    VStack(spacing: 16) {
      Image(systemName: "person.crop.circle.badge.exclamationmark")
        .font(.system(size: 64))
        .foregroundColor(.secondary)
      Text("You're not signed in")
        .font(.title3.weight(.semibold))
      Text("Sign in to see your tickets and team news.")
        .font(.subheadline)
        .foregroundColor(.secondary)
        .multilineTextAlignment(.center)
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
      .padding(.top, 8)
    }
    .padding(24)
  }

  private var loggedInView: some View {
    ScrollView {
      VStack(spacing: 16) {
        userHeader
        tabSelector
        matchesList
      }
      .padding(16)
    }
  }

  private var userHeader: some View {
    HStack(spacing: 12) {
      Circle()
        .fill(Theme.brandGreen.opacity(0.15))
        .frame(width: 56, height: 56)
        .overlay(
          Text(initials)
            .font(.headline.weight(.bold))
            .foregroundColor(Theme.brandGreen)
        )
      VStack(alignment: .leading, spacing: 4) {
        Text(vm.username.isEmpty ? "Anonymous" : vm.username)
          .font(.title3.weight(.semibold))
        Text("\(vm.upcomingMatches.count) upcoming · \(vm.visitedMatches.count) attended")
          .font(.caption)
          .foregroundColor(.secondary)
      }
      Spacer()
    }
    .padding(16)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 12))
  }

  private var tabSelector: some View {
    Picker("", selection: $tab) {
      Text("Upcoming").tag(ProfileTab.upcoming)
      Text("Visited").tag(ProfileTab.visited)
    }
    .pickerStyle(.segmented)
  }

  @ViewBuilder
  private var matchesList: some View {
    let list = tab == .upcoming ? vm.upcomingMatches : vm.visitedMatches
    if list.isEmpty {
      VStack(spacing: 8) {
        Image(systemName: "ticket")
          .font(.largeTitle)
          .foregroundColor(.secondary)
        Text(tab == .upcoming ? "No upcoming matches" : "No visited matches")
          .font(.headline)
        Text("Your tickets will show here once you buy one.")
          .font(.subheadline)
          .foregroundColor(.secondary)
      }
      .frame(maxWidth: .infinity)
      .padding(.top, 40)
    } else {
      VStack(spacing: 8) {
        ForEach(list, id: \.id) { match in
          PersistedMatchRow(match: match)
        }
      }
    }
  }

  private var initials: String {
    let trimmed = vm.username.trimmingCharacters(in: .whitespaces)
    guard !trimmed.isEmpty else { return "?" }
    let parts = trimmed.split(separator: " ")
    if parts.count >= 2 {
      return String(parts[0].prefix(1) + parts[1].prefix(1)).uppercased()
    }
    return String(trimmed.prefix(2)).uppercased()
  }
}

private struct PersistedMatchRow: View {
  let match: PersistedMatch

  var body: some View {
    VStack(alignment: .leading, spacing: 4) {
      Text("\(match.homeTeamName) vs \(match.awayTeamName)")
        .font(.subheadline.weight(.semibold))
      Text("\(match.date) · \(match.time)")
        .font(.caption)
        .foregroundColor(.secondary)
      if let venue = match.venue, !venue.isEmpty {
        Text(venue)
          .font(.caption)
          .foregroundColor(.secondary)
      }
    }
    .frame(maxWidth: .infinity, alignment: .leading)
    .padding(12)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 10))
  }
}
