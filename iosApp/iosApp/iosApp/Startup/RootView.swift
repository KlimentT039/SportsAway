import SwiftUI
import Shared

/// Gates `ContentView` on the shared `StartupViewModel`.
/// Shows the splash until the VM resolves; afterwards, the tab view takes over.
struct RootView: View {
  @StateObject private var vm = StartupViewModelObservable()

  var body: some View {
    ZStack {
      if vm.isReady {
        ContentView()
          .transition(.opacity)
      } else {
        SplashView()
          .transition(.opacity)
      }
    }
    .animation(.easeOut(duration: 0.25), value: vm.isReady)
    .onAppear { vm.start() }
  }
}
