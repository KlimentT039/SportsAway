import Foundation
import Combine
import Shared

@MainActor
final class EventsOverviewViewModelObservable: ObservableObject {
  let viewModel: EventsOverviewViewModel

  @Published var isLoading: Bool = true
  @Published var isError: Bool = false
  @Published var isEmptySearchResult: Bool = false
  @Published var groupedMatches: [GroupedMatch] = []
  @Published var searchQuery: String = ""

  private var stateObserver: FlowObserver?
  private var queryObserver: FlowObserver?

  init(competitionId: Int32?) {
    let boxedId: KotlinInt? = competitionId.map { KotlinInt(int: $0) }
    self.viewModel = KoinHelper.shared.eventsOverviewViewModel(competitionId: boxedId)

    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is EventsViewStateLoading {
        self.isLoading = true
        self.isError = false
        self.isEmptySearchResult = false
      } else if let data = value as? EventsViewStateEventData {
        self.isLoading = false
        self.isError = false
        self.isEmptySearchResult = false
        self.groupedMatches = (data.groupedMatches as? [GroupedMatch]) ?? []
      } else if value is EventsViewStateEmptySearchResult {
        self.isLoading = false
        self.isError = false
        self.isEmptySearchResult = true
        self.groupedMatches = []
      } else if value is EventsViewStateError {
        self.isLoading = false
        self.isError = true
        self.isEmptySearchResult = false
        self.groupedMatches = []
      }
    }

    queryObserver = FlowObserver(viewModel.searchQuery)
    queryObserver?.watch { [weak self] value in
      guard let self else { return }
      if let str = value as? String {
        self.searchQuery = str
      }
    }
  }

  func onSearchQueryChanged(_ query: String) {
    viewModel.onSearchQuery(query: query)
  }
}
