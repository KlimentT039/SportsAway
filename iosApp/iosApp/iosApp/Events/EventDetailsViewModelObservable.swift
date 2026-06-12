import Foundation
import Shared

@MainActor
final class EventDetailsViewModelObservable: ObservableObject {
  let viewModel: EventDetailsViewModel = KoinHelper.shared.eventDetailsViewModel()

  @Published var isLoading: Bool = true
  @Published var isError: Bool = false
  @Published var match: Match? = nil
  @Published var availableTickets: [Ticket] = []
  @Published var selectedTicket: Ticket? = nil
  @Published var selectedFilter: TicketFilter? = nil
  @Published var pendingOrder: OrderBundle? = nil
  @Published var presentOrder: Bool = false

  private var stateObserver: FlowObserver?
  private var eventObserver: FlowObserver?

  init(matchId: Int32) {
    stateObserver = FlowObserver(viewModel.state)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is EventDetailsViewStateLoading {
        self.isLoading = true
        self.isError = false
      } else if let data = value as? EventDetailsViewStateEventData {
        self.isLoading = false
        self.isError = false
        self.match = data.match
        self.availableTickets = (data.availableTickets as? [Ticket]) ?? []
        self.selectedTicket = data.selectedTicket
        self.selectedFilter = data.selectedFilter
      } else if value is EventDetailsViewStateShowError {
        self.isLoading = false
        self.isError = true
      }
    }

    eventObserver = FlowObserver(viewModel.event)
    eventObserver?.watch { [weak self] value in
      guard let self else { return }
      if let proceed = value as? DetailsEventProceedToOrderScreen {
        self.pendingOrder = proceed.orderBundle
        self.presentOrder = true
      }
    }

    viewModel.initData(matchId: matchId)
  }

  func onTicketTap(_ ticket: Ticket) {
    viewModel.onTicketClicked(ticket: ticket)
  }

  func onFilterTap(_ filter: TicketFilter) {
    viewModel.onTicketFilterSelected(ticketFilter: filter)
  }

  func onBuyTap() {
    viewModel.onBuyButtonClicked()
  }

  func dismissOrder() {
    presentOrder = false
    pendingOrder = nil
  }
}
