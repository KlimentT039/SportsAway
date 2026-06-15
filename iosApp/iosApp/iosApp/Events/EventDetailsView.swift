import SwiftUI
import Combine
import Shared

struct EventDetailsView: View {
  let matchId: Int32
  let title: String

  @StateObject private var vm: EventDetailsViewModelObservable

  init(matchId: Int32, title: String) {
    self.matchId = matchId
    self.title = title
    _vm = StateObject(wrappedValue: EventDetailsViewModelObservable(matchId: matchId))
  }

  var body: some View {
    ScrollView {
      VStack(spacing: 16) {
        if vm.isLoading {
          ProgressView().tint(Theme.brandGreen).padding(.top, 80)
        } else if vm.isError {
          ErrorBanner(message: "Couldn't load this match. Try again later.")
            .padding(.horizontal, 16)
        } else if let match = vm.match {
          MatchHeaderCard(match: match)
            .padding(.horizontal, 16)

          filterRow
            .padding(.horizontal, 16)

          ticketList(match: match)
            .padding(.horizontal, 16)
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
    .safeAreaInset(edge: .bottom) {
      if vm.match != nil {
        buyBar
      }
    }
    .sheet(isPresented: $vm.presentOrder, onDismiss: { vm.dismissOrder() }) {
      if let bundle = vm.pendingOrder {
        OrderTicketsView(bundle: bundle)
      }
    }
  }

  private var filterRow: some View {
    HStack(spacing: 8) {
      FilterChip(label: "General", selected: vm.selectedFilter == TicketFilter.general) {
        vm.onFilterTap(TicketFilter.general)
      }
      FilterChip(label: "VIP", selected: vm.selectedFilter == TicketFilter.vip) {
        vm.onFilterTap(TicketFilter.vip)
      }
      Spacer()
    }
  }

  private func ticketList(match: Match) -> some View {
    VStack(spacing: 8) {
      ForEach(Array(vm.availableTickets.enumerated()), id: \.offset) { _, ticket in
        TicketRow(ticket: ticket, selected: vm.selectedTicket == ticket) {
          vm.onTicketTap(ticket)
        }
      }
      if vm.availableTickets.isEmpty {
        Text("No tickets available for the selected filter.")
          .font(.subheadline)
          .foregroundColor(.secondary)
          .padding(.top, 12)
      }
    }
  }

  private var buyBar: some View {
    HStack {
      VStack(alignment: .leading, spacing: 2) {
        Text("Selected")
          .font(.caption)
          .foregroundColor(.secondary)
        Text(selectedSummary)
          .font(.subheadline.weight(.semibold))
      }
      Spacer()
      Button {
        vm.onBuyTap()
      } label: {
        Text("Buy")
          .font(.headline)
          .foregroundColor(.white)
          .padding(.horizontal, 24)
          .padding(.vertical, 12)
          .background(vm.selectedTicket == nil ? Color.gray : Theme.brandGreen)
          .clipShape(Capsule())
      }
      .disabled(vm.selectedTicket == nil)
    }
    .padding(.horizontal, 16)
    .padding(.vertical, 12)
    .background(Color(.systemBackground).shadow(.drop(color: .black.opacity(0.08), radius: 3, y: -1)))
  }

  private var selectedSummary: String {
    guard let t = vm.selectedTicket else { return "Pick a ticket" }
    return "Section \(t.section) · $\(t.price)"
  }
}

private struct MatchHeaderCard: View {
  let match: Match

  var body: some View {
    VStack(spacing: 12) {
      Text(match.competition.name)
        .font(.subheadline.weight(.semibold))
        .foregroundColor(.secondary)
      HStack {
        teamColumn(team: match.homeTeam)
        VStack(spacing: 4) {
          Text("vs")
            .font(.title3.bold())
            .foregroundColor(.secondary)
          Text("MD \(match.matchday)")
            .font(.caption)
            .foregroundColor(.secondary)
        }
        .padding(.horizontal, 16)
        teamColumn(team: match.awayTeam)
      }
      VStack(spacing: 4) {
        Text("\(match.date) · \(match.time)")
          .font(.subheadline)
        if let venue = match.venue, !venue.isEmpty {
          Text(venue)
            .font(.footnote)
            .foregroundColor(.secondary)
            .multilineTextAlignment(.center)
        }
      }
    }
    .padding(16)
    .frame(maxWidth: .infinity)
    .background(Color(.systemBackground))
    .clipShape(RoundedRectangle(cornerRadius: 12))
    .shadow(color: .black.opacity(0.05), radius: 4, y: 1)
  }

  private func teamColumn(team: Team) -> some View {
    VStack(spacing: 6) {
      crestImage(url: team.crest, name: team.shortName, size: 56)
      Text(team.shortName)
        .font(.subheadline.weight(.medium))
        .lineLimit(1)
        .multilineTextAlignment(.center)
    }
    .frame(maxWidth: .infinity)
  }
}

private struct FilterChip: View {
  let label: String
  let selected: Bool
  let action: () -> Void

  var body: some View {
    Button(action: action) {
      Text(label)
        .font(.subheadline.weight(.semibold))
        .padding(.horizontal, 16)
        .padding(.vertical, 8)
        .background(selected ? Theme.brandGreen : Color(.systemBackground))
        .foregroundColor(selected ? .white : .primary)
        .clipShape(Capsule())
        .overlay(
          Capsule().stroke(selected ? Color.clear : Color(.separator), lineWidth: 0.5)
        )
    }
  }
}

private struct TicketRow: View {
  let ticket: Ticket
  let selected: Bool
  let action: () -> Void

  var body: some View {
    Button(action: action) {
      HStack(spacing: 12) {
        Image(systemName: selected ? "checkmark.circle.fill" : "circle")
          .foregroundColor(selected ? Theme.brandGreen : .secondary)
        VStack(alignment: .leading, spacing: 4) {
          Text(ticketTitleString)
            .font(.subheadline.weight(.semibold))
          Text(detailString)
            .font(.caption)
            .foregroundColor(.secondary)
        }
        Spacer()
        Text("$\(ticket.price)")
          .font(.headline.weight(.bold))
          .foregroundColor(Theme.brandGreen)
      }
      .padding(12)
      .background(Color(.systemBackground))
      .clipShape(RoundedRectangle(cornerRadius: 10))
      .overlay(
        RoundedRectangle(cornerRadius: 10)
          .stroke(selected ? Theme.brandGreen : Color.clear, lineWidth: 1.5)
      )
    }
    .buttonStyle(.plain)
  }

  private var ticketTitleString: String {
    if ticket.title == TicketTitle.shortSide { return "Short side" }
    if ticket.title == TicketTitle.longSide { return "Long side" }
    if ticket.title == TicketTitle.vip { return "VIP" }
    return "Ticket"
  }

  private var detailString: String {
    let row = ticket.row?.intValue.description ?? "-"
    return "Section \(ticket.section) · Row \(row) · \(ticket.remainingTickets) left"
  }
}
