import Foundation
import Shared

@MainActor
final class FavouriteViewModelObservable: ObservableObject {
  let viewModel: FavouriteViewModel = KoinHelper.shared.favouriteViewModel()

  @Published var isLoading: Bool = true
  @Published var isError: Bool = false
  @Published var notLoggedIn: Bool = false
  @Published var noTeamsSelected: Bool = false
  @Published var favouriteTeams: [FavouriteTeam] = []

  private var stateObserver: FlowObserver?
  private var teamsObserver: FlowObserver?

  init() {
    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is FavouriteViewStateLoading {
        self.isLoading = true
        self.isError = false
        self.notLoggedIn = false
        self.noTeamsSelected = false
      } else if value is FavouriteViewStateHasNotLoggedIn {
        self.isLoading = false
        self.notLoggedIn = true
      } else if value is FavouriteViewStateShowError {
        self.isLoading = false
        self.isError = true
      } else if value is FavouriteViewStateHasNotSelectedTeams {
        self.isLoading = false
        self.noTeamsSelected = true
      } else if value is FavouriteViewStateTeamsAndMatches {
        self.isLoading = false
      }
    }

    teamsObserver = FlowObserver(viewModel.favouriteTeams)
    teamsObserver?.watch { [weak self] value in
      guard let self else { return }
      self.favouriteTeams = (value as? [FavouriteTeam]) ?? []
    }
  }

  func reload() {
    viewModel.initScreen()
  }
}
