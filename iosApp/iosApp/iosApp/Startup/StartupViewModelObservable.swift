import Foundation
import Combine
import Shared

@MainActor
final class StartupViewModelObservable: ObservableObject {
  let viewModel: StartupViewModel = KoinHelper.shared.startupViewModel()

  @Published var isReady: Bool = false
  @Published var isLoggedIn: Bool = false

  private var stateObserver: FlowObserver?

  init() {
    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if let ready = value as? StartupViewStateReady {
        self.isLoggedIn = ready.isLoggedIn
        self.isReady = true
      }
    }
  }

  func start() {
    viewModel.resolve()
  }
}
