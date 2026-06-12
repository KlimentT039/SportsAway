import SwiftUI
import Shared

struct ContentView: View {
  @State private var presentAuth: Bool = false
  @State private var selectedTab: MainTab = .home

  enum MainTab: Hashable { case home, events, favourites, profile }

  var body: some View {
    TabView(selection: $selectedTab) {
      HomeView()
        .tabItem { Label("Home", systemImage: "house.fill") }
        .tag(MainTab.home)

      EventsRootView()
        .tabItem { Label("Events", systemImage: "calendar") }
        .tag(MainTab.events)

      FavouritesView(onLoginTapped: { presentAuth = true })
        .tabItem { Label("Favourites", systemImage: "heart.fill") }
        .tag(MainTab.favourites)

      ProfileView(onLoginTapped: { presentAuth = true })
        .tabItem { Label("Profile", systemImage: "person.fill") }
        .tag(MainTab.profile)
    }
    .tint(Theme.brandGreen)
    .sheet(isPresented: $presentAuth) {
      LoginView(
        onSuccess: { presentAuth = false },
        onCancel: { presentAuth = false }
      )
    }
  }
}

private struct EventsRootView: View {
  @State private var path: [AppRoute] = []

  var body: some View {
    NavigationStack(path: $path) {
      EventsOverviewView(competitionId: nil, title: "All events")
        .navigationDestination(for: AppRoute.self) { route in
          AppRouteDestination(route: route)
        }
    }
  }
}

#Preview {
  ContentView()
}
