import Foundation
import Shared

@MainActor
final class ProfileViewModelObservable: ObservableObject {
  let viewModel: ProfileViewModel = KoinHelper.shared.profileViewModel()

  @Published var isLoading: Bool = true
  @Published var isError: Bool = false
  @Published var notLoggedIn: Bool = false
  @Published var username: String = ""
  @Published var visitedMatches: [PersistedMatch] = []
  @Published var upcomingMatches: [PersistedMatch] = []
  @Published var navigateToDashboard: Bool = false

  private var stateObserver: FlowObserver?
  private var eventObserver: FlowObserver?

  init() {
    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is ProfileViewStateLoading {
        self.isLoading = true
        self.isError = false
        self.notLoggedIn = false
      } else if value is ProfileViewStateUserHasNotLoggedIn {
        self.isLoading = false
        self.notLoggedIn = true
        self.isError = false
      } else if value is ProfileViewStateShowError {
        self.isLoading = false
        self.isError = true
        self.notLoggedIn = false
      } else if let data = value as? ProfileViewStateProfileData {
        self.isLoading = false
        self.isError = false
        self.notLoggedIn = false
        self.username = data.user.username
        self.visitedMatches = (data.user.visitedMatches as? [PersistedMatch]) ?? []
        self.upcomingMatches = (data.user.upcomingMatches as? [PersistedMatch]) ?? []
      }
    }

    eventObserver = FlowObserver(viewModel.event)
    eventObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is ProfileEventsNavigateToDashboard {
        self.navigateToDashboard = true
      }
    }
  }

  func reload() {
    viewModel.requestState()
  }

  func logout() {
    viewModel.onLogout()
  }
}
