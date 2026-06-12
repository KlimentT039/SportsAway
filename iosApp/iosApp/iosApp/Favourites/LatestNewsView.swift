import SwiftUI
import Shared

@MainActor
final class LatestNewsViewModelObservable: ObservableObject {
  let viewModel: LatestNewsViewModel = KoinHelper.shared.latestNewsViewModel()

  @Published var isLoading: Bool = true
  @Published var isError: Bool = false
  @Published var team: Team? = nil
  @Published var news: [News] = []
  @Published var injuries: [InjuryReport] = []

  private var stateObserver: FlowObserver?

  init(teamId: Int32) {
    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is LatestNewsViewStateLoading {
        self.isLoading = true
        self.isError = false
      } else if value is LatestNewsViewStateError {
        self.isLoading = false
        self.isError = true
      } else if let content = value as? LatestNewsViewStateContent {
        self.isLoading = false
        self.isError = false
        self.team = content.teamInfo.team
        self.news = (content.teamInfo.teamInfo.latestNews as? [News]) ?? []
        self.injuries = (content.teamInfo.teamInfo.injuryReports as? [InjuryReport]) ?? []
      }
    }
    viewModel.initData(id: teamId)
  }
}

struct LatestNewsView: View {
  let teamId: Int32
  let teamName: String
  @StateObject private var vm: LatestNewsViewModelObservable

  init(teamId: Int32, teamName: String) {
    self.teamId = teamId
    self.teamName = teamName
    _vm = StateObject(wrappedValue: LatestNewsViewModelObservable(teamId: teamId))
  }

  var body: some View {
    ScrollView {
      VStack(alignment: .leading, spacing: 16) {
        if vm.isLoading {
          ProgressView().tint(Theme.brandGreen).padding(.top, 80)
        } else if vm.isError {
          ErrorBanner(message: "Couldn't load team news.")
            .padding(.horizontal, 16)
        } else {
          if !vm.news.isEmpty {
            sectionHeader("Latest news")
            VStack(spacing: 8) {
              ForEach(Array(vm.news.enumerated()), id: \.offset) { _, item in
                NewsRow(item: item)
              }
            }
            .padding(.horizontal, 16)
          }
          if !vm.injuries.isEmpty {
            sectionHeader("Injuries")
            VStack(spacing: 8) {
              ForEach(Array(vm.injuries.enumerated()), id: \.offset) { _, item in
                InjuryRow(item: item)
              }
            }
            .padding(.horizontal, 16)
          }
          if vm.news.isEmpty && vm.injuries.isEmpty {
            Text("No updates for \(teamName).")
              .font(.subheadline)
              .foregroundColor(.secondary)
              .padding(.horizontal, 16)
          }
        }
      }
      .padding(.vertical, 12)
    }
    .background(Color(.systemGroupedBackground))
    .navigationTitle(teamName)
    .navigationBarTitleDisplayMode(.inline)
  }

  private func sectionHeader(_ title: String) -> some View {
    Text(title)
      .font(.headline.weight(.semibold))
      .padding(.horizontal, 16)
  }
}

private struct NewsRow: View {
  let item: News

  var body: some View {
    VStack(alignment: .leading, spacing: 4) {
      Text(item.title)
        .font(.subheadline.weight(.semibold))
      Text(item.date)
        .font(.caption)
        .foregroundColor(.secondary)
      Text(item.summary)
        .font(.footnote)
        .foregroundColor(.secondary)
        .lineLimit(3)
    }
    .frame(maxWidth: .infinity, alignment: .leading)
    .padding(12)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 10))
  }
}

private struct InjuryRow: View {
  let item: InjuryReport

  var body: some View {
    HStack {
      VStack(alignment: .leading, spacing: 4) {
        Text(item.player).font(.subheadline.weight(.semibold))
        Text(item.injury).font(.caption).foregroundColor(.secondary)
        Text("Back: \(item.expectedReturn)").font(.caption2).foregroundColor(.secondary)
      }
      Spacer()
      Text(item.status)
        .font(.caption.weight(.semibold))
        .padding(.horizontal, 8)
        .padding(.vertical, 4)
        .background(Theme.accentRed.opacity(0.15))
        .foregroundColor(Theme.accentRed)
        .clipShape(Capsule())
    }
    .padding(12)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 10))
  }
}
