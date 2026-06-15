import SwiftUI
import Combine
import Shared

struct EventsOverviewView: View {
  let competitionId: Int32?
  let title: String

  @StateObject private var vm: EventsOverviewViewModelObservable
  @State private var localSearchText: String = ""

  init(competitionId: Int32?, title: String) {
    self.competitionId = competitionId
    self.title = title
    _vm = StateObject(wrappedValue: EventsOverviewViewModelObservable(competitionId: competitionId))
  }

  var body: some View {
    ScrollView {
      VStack(alignment: .leading, spacing: 16) {
        SearchField(text: $localSearchText, placeholder: "Search team or competition")
          .padding(.horizontal, 16)
          .onChange(of: localSearchText) { _, newValue in
            vm.onSearchQueryChanged(newValue)
          }

        if vm.isLoading {
          loadingState
        } else if vm.isError {
          ErrorBanner(message: "Couldn't load events. Try again later.")
            .padding(.horizontal, 16)
        } else if vm.isEmptySearchResult {
          emptyState
        } else {
          ForEach(vm.groupedMatches, id: \.competition.id) { group in
            groupSection(group)
          }
        }
      }
      .padding(.vertical, 12)
    }
    .background(Color(.systemGroupedBackground))
    .navigationTitle(title)
    .navigationBarTitleDisplayMode(.inline)
    .toolbarBackground(Theme.brandGreen, for: .navigationBar)
    .toolbarBackground(.visible, for: .navigationBar)
    .toolbarColorScheme(.dark, for: .navigationBar)
  }

  private var loadingState: some View {
    HStack {
      Spacer()
      ProgressView().tint(Theme.brandGreen)
      Spacer()
    }
    .padding(.top, 60)
  }

  private var emptyState: some View {
    VStack(spacing: 8) {
      Image(systemName: "magnifyingglass")
        .font(.largeTitle)
        .foregroundColor(.secondary)
      Text("No matches for \"\(localSearchText)\"")
        .font(.headline)
      Text("Try a shorter or different query.")
        .font(.subheadline)
        .foregroundColor(.secondary)
    }
    .frame(maxWidth: .infinity)
    .padding(.top, 60)
  }

  private func groupSection(_ group: GroupedMatch) -> some View {
    VStack(alignment: .leading, spacing: 8) {
      if group.showSection {
        HStack(spacing: 8) {
          crestImage(url: group.competition.emblem, name: group.competition.name, size: 24)
          Text(group.competition.name)
            .font(.headline.weight(.semibold))
          Spacer()
        }
        .padding(.horizontal, 16)
      }

      VStack(spacing: 8) {
        ForEach(group.matches, id: \.id) { match in
          NavigationLink(value: AppRoute.eventDetails(matchId: match.id, title: "\(match.homeTeam.shortName) vs \(match.awayTeam.shortName)")) {
            MatchRow(match: match)
          }
          .buttonStyle(.plain)
        }
      }
      .padding(.horizontal, 16)
    }
  }
}

private struct SearchField: View {
  @Binding var text: String
  let placeholder: String

  var body: some View {
    HStack(spacing: 8) {
      Image(systemName: "magnifyingglass")
        .foregroundColor(.secondary)
      TextField(placeholder, text: $text)
        .textInputAutocapitalization(.never)
        .autocorrectionDisabled()
      if !text.isEmpty {
        Button {
          text = ""
        } label: {
          Image(systemName: "xmark.circle.fill")
            .foregroundColor(.secondary)
        }
      }
    }
    .padding(.horizontal, 12)
    .padding(.vertical, 10)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 10))
  }
}

private struct MatchRow: View {
  let match: Match

  var body: some View {
    HStack(spacing: 12) {
      VStack(alignment: .leading, spacing: 4) {
        HStack {
          Text(match.homeTeam.shortName)
            .font(.subheadline.weight(.semibold))
            .lineLimit(1)
          Text("vs").foregroundColor(.secondary).font(.caption)
          Text(match.awayTeam.shortName)
            .font(.subheadline.weight(.semibold))
            .lineLimit(1)
          Spacer()
        }
        Text(matchSubtitle)
          .font(.caption)
          .foregroundColor(.secondary)
          .lineLimit(1)
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

  private var matchSubtitle: String {
    let datePart = "\(match.date) \(match.time)".trimmingCharacters(in: .whitespaces)
    guard let venue = match.venue, !venue.isEmpty else { return datePart }
    return datePart.isEmpty ? venue : "\(datePart) · \(venue)"
  }
}
