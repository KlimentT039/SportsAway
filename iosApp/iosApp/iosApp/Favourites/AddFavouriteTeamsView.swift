import SwiftUI
import Shared

@MainActor
final class AddFavouriteTeamsViewModelObservable: ObservableObject {
  let viewModel: AddFavouriteTeamsViewModel = KoinHelper.shared.addFavouriteTeamsViewModel()

  @Published var isLoading: Bool = true
  @Published var isError: Bool = false
  @Published var teams: [Team] = []
  @Published var selectedTeams: [Int32] = []
  @Published var searchQuery: String = ""
  @Published var done: Bool = false

  private var stateObserver: FlowObserver?
  private var queryObserver: FlowObserver?
  private var eventObserver: FlowObserver?

  init() {
    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is ViewStateLoading {
        self.isLoading = true
        self.isError = false
      } else if let data = value as? ViewStateTeamsData {
        self.isLoading = false
        self.teams = (data.teams as? [Team]) ?? []
        self.selectedTeams = ((data.selectedTeams as? [KotlinInt]) ?? []).map { $0.int32Value }
      } else if value is ViewStateError {
        self.isLoading = false
        self.isError = true
      }
    }

    queryObserver = FlowObserver(viewModel.searchQuery)
    queryObserver?.watch { [weak self] value in
      guard let self else { return }
      if let s = value as? String {
        self.searchQuery = s
      }
    }

    eventObserver = FlowObserver(viewModel.event)
    eventObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is FavouriteEventDone {
        self.done = true
      }
    }
  }

  func onSearchChange(_ q: String) {
    viewModel.onSearchValueChanged(searchQuery: q)
  }

  func onFavTap(_ team: Team) {
    viewModel.onFavouriteClicked(team: team)
  }

  func onDoneTap() {
    viewModel.onDoneClicked()
  }
}

struct AddFavouriteTeamsView: View {
  @StateObject private var vm = AddFavouriteTeamsViewModelObservable()
  @Environment(\.dismiss) private var dismiss
  @State private var localQuery: String = ""

  var body: some View {
    Group {
      if vm.isLoading {
        ProgressView().tint(Theme.brandGreen)
      } else if vm.isError {
        ErrorBanner(message: "Couldn't load teams.")
          .padding(.horizontal, 16)
      } else {
        content
      }
    }
    .frame(maxWidth: .infinity, maxHeight: .infinity)
    .background(Color(.systemGroupedBackground))
    .navigationTitle("Pick your teams")
    .navigationBarTitleDisplayMode(.inline)
    .toolbar {
      ToolbarItem(placement: .topBarTrailing) {
        Button("Done") { vm.onDoneTap() }
          .foregroundColor(.white)
          .fontWeight(.semibold)
      }
    }
    .onChange(of: vm.done) { _, newValue in
      if newValue { dismiss() }
    }
  }

  private var content: some View {
    VStack(spacing: 0) {
      HStack(spacing: 8) {
        Image(systemName: "magnifyingglass").foregroundColor(.secondary)
        TextField("Search teams", text: $localQuery)
          .textInputAutocapitalization(.never)
          .autocorrectionDisabled()
          .onChange(of: localQuery) { _, newValue in
            vm.onSearchChange(newValue)
          }
      }
      .padding(.horizontal, 12)
      .padding(.vertical, 10)
      .background(Color(.systemBackground))
      .clipShape(RoundedRectangle(cornerRadius: 10))
      .padding(16)

      ScrollView {
        LazyVStack(spacing: 8) {
          ForEach(vm.teams, id: \.id) { team in
            Button {
              vm.onFavTap(team)
            } label: {
              HStack(spacing: 12) {
                crestImage(url: team.crest, size: 32)
                VStack(alignment: .leading, spacing: 2) {
                  Text(team.name)
                    .font(.subheadline.weight(.semibold))
                  if let country = team.country {
                    Text(country)
                      .font(.caption)
                      .foregroundColor(.secondary)
                  }
                }
                Spacer()
                Image(systemName: vm.selectedTeams.contains(team.id) ? "heart.fill" : "heart")
                  .foregroundColor(vm.selectedTeams.contains(team.id) ? Theme.accentRed : .secondary)
              }
              .padding(12)
              .background(Color(.systemBackground))
              .clipShape(RoundedRectangle(cornerRadius: 10))
              .foregroundColor(.primary)
            }
          }
        }
        .padding(.horizontal, 16)
        .padding(.bottom, 16)
      }
    }
  }
}
