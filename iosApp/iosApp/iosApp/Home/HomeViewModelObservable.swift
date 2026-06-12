import Foundation
import Shared

@MainActor
final class HomeViewModelObservable: ObservableObject {
  let viewModel: HomeViewModel = KoinHelper.shared.homeViewModel()

  @Published var isLoading: Bool = true
  @Published var trendingMatches: [Match] = []
  @Published var competitions: [Competition] = []
  @Published var hasError: Bool = false

  private var stateObserver: FlowObserver?

  init() {
    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is ViewStateLoading {
        self.isLoading = true
        self.hasError = false
      } else if let data = value as? ViewStateHomeData {
        self.isLoading = false
        self.trendingMatches = (data.matchData.listOfNextMatches as? [Match]) ?? []
        self.competitions = (data.competitionsData.listOfCompetitions as? [Competition]) ?? []
        self.hasError = data.matchData.isError || data.competitionsData.isError
      }
    }
  }
}
