import SwiftUI
import Shared

@MainActor
final class OrderTicketsViewModelObservable: ObservableObject {
  let viewModel: OrderTicketsViewModel = KoinHelper.shared.orderTicketsViewModel()

  @Published var isLoading: Bool = true
  @Published var match: Match? = nil
  @Published var ticket: Ticket? = nil
  @Published var numberOfTickets: Int32 = 2
  @Published var total: Int32 = 0
  @Published var hasCard: Bool = false
  @Published var hasBilling: Bool = false
  @Published var isButtonEnabled: Bool = false
  @Published var orderSucceeded: Bool = false

  private var stateObserver: FlowObserver?

  init(bundle: OrderBundle) {
    stateObserver = FlowObserver(viewModel.state)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is OrderTicketsStateLoading {
        self.isLoading = true
      } else if let data = value as? OrderTicketsStateOrderTicketsData {
        self.isLoading = false
        self.match = data.match
        self.ticket = data.ticket
        self.numberOfTickets = data.numberOfTickets
        self.total = data.total
        self.hasCard = data.cardData != nil
        self.hasBilling = data.billingAddress != nil
        self.isButtonEnabled = data.isButtonEnabled
        self.orderSucceeded = data.showOrderIsSuccessful
      }
    }
    viewModel.initData(orderBundle: bundle)
  }

  func adjustTickets(_ count: Int32) {
    viewModel.onSelectNumOfTickets(numberOfTickets: count)
  }

  func saveCardStub() {
    viewModel.onSaveCardClicked(
      cardName: "Test Cardholder",
      cardNum: "4242 4242 4242 4242",
      expDate: "12/29",
      cvv: "123"
    )
  }

  func saveAddressStub() {
    viewModel.onSaveBillingAddress(
      fullName: "Test User",
      addressLine1: "1 Test St",
      addressLine2: "",
      city: "Skopje",
      zipCode: "1000",
      country: "North Macedonia"
    )
  }

  func confirm() {
    viewModel.slideOrderComplete()
  }
}

struct OrderTicketsView: View {
  let bundle: OrderBundle
  @StateObject private var vm: OrderTicketsViewModelObservable
  @Environment(\.dismiss) private var dismiss

  init(bundle: OrderBundle) {
    self.bundle = bundle
    _vm = StateObject(wrappedValue: OrderTicketsViewModelObservable(bundle: bundle))
  }

  var body: some View {
    NavigationStack {
      ScrollView {
        VStack(spacing: 16) {
          if vm.isLoading {
            ProgressView().tint(Theme.brandGreen).padding(.top, 80)
          } else if vm.orderSucceeded {
            successView
          } else {
            orderForm
          }
        }
        .padding(16)
      }
      .background(Color(.systemGroupedBackground))
      .navigationTitle("Order tickets")
      .navigationBarTitleDisplayMode(.inline)
      .toolbar {
        ToolbarItem(placement: .topBarLeading) {
          Button("Close") { dismiss() }
        }
      }
    }
  }

  private var orderForm: some View {
    VStack(spacing: 16) {
      if let match = vm.match, let ticket = vm.ticket {
        summaryCard(match: match, ticket: ticket)
      }
      ticketCountStepper
      paymentSection
      addressSection
      confirmButton
    }
  }

  private func summaryCard(match: Match, ticket: Ticket) -> some View {
    VStack(alignment: .leading, spacing: 8) {
      Text("\(match.homeTeam.shortName) vs \(match.awayTeam.shortName)")
        .font(.headline.weight(.bold))
      Text("\(match.date) · \(match.time)")
        .font(.subheadline)
        .foregroundColor(.secondary)
      Divider()
      HStack {
        Text("Section \(ticket.section)")
        Spacer()
        Text("$\(ticket.price) each")
      }
      .font(.subheadline)
    }
    .padding(16)
    .frame(maxWidth: .infinity, alignment: .leading)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 12))
  }

  private var ticketCountStepper: some View {
    HStack {
      Text("Tickets")
        .font(.subheadline.weight(.semibold))
      Spacer()
      Stepper(value: Binding(
        get: { Int(vm.numberOfTickets) },
        set: { vm.adjustTickets(Int32($0)) }
      ), in: 1...10) {
        Text("\(vm.numberOfTickets)")
          .font(.headline)
      }
      .labelsHidden()
      Text("\(vm.numberOfTickets)")
        .font(.headline)
        .frame(minWidth: 24)
    }
    .padding(16)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 12))
  }

  private var paymentSection: some View {
    Button {
      vm.saveCardStub()
    } label: {
      HStack {
        Image(systemName: vm.hasCard ? "checkmark.circle.fill" : "creditcard")
          .foregroundColor(vm.hasCard ? Theme.brandGreen : .secondary)
        Text(vm.hasCard ? "Card added (•••• 4242)" : "Add card")
        Spacer()
        Image(systemName: "chevron.right").font(.caption).foregroundColor(.secondary)
      }
      .padding(16)
      .background(Color(.systemBackground))
      .clipShape(RoundedRectangle(cornerRadius: 12))
      .foregroundColor(.primary)
    }
  }

  private var addressSection: some View {
    Button {
      vm.saveAddressStub()
    } label: {
      HStack {
        Image(systemName: vm.hasBilling ? "checkmark.circle.fill" : "house")
          .foregroundColor(vm.hasBilling ? Theme.brandGreen : .secondary)
        Text(vm.hasBilling ? "Billing address added" : "Add billing address")
        Spacer()
        Image(systemName: "chevron.right").font(.caption).foregroundColor(.secondary)
      }
      .padding(16)
      .background(Color(.systemBackground))
      .clipShape(RoundedRectangle(cornerRadius: 12))
      .foregroundColor(.primary)
    }
  }

  private var confirmButton: some View {
    Button {
      vm.confirm()
    } label: {
      HStack {
        Text("Confirm · $\(vm.total)")
          .font(.headline)
          .foregroundColor(.white)
      }
      .frame(maxWidth: .infinity)
      .padding(.vertical, 14)
      .background(vm.isButtonEnabled ? Theme.brandGreen : Color.gray)
      .clipShape(RoundedRectangle(cornerRadius: 12))
    }
    .disabled(!vm.isButtonEnabled)
  }

  private var successView: some View {
    VStack(spacing: 16) {
      Image(systemName: "checkmark.seal.fill")
        .font(.system(size: 80))
        .foregroundColor(Theme.brandGreen)
      Text("Order confirmed")
        .font(.title.weight(.bold))
      Text("Your tickets are on the way.")
        .font(.subheadline)
        .foregroundColor(.secondary)
      Button("Done") { dismiss() }
        .font(.headline)
        .foregroundColor(.white)
        .padding(.horizontal, 32)
        .padding(.vertical, 12)
        .background(Theme.brandGreen)
        .clipShape(Capsule())
        .padding(.top, 16)
    }
    .padding(.top, 60)
  }
}
