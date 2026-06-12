import SwiftUI
import Shared

@main
struct iosAppApp: App {
  init() {
    KoinIosKt.doInitKoin()
  }

  var body: some Scene {
    WindowGroup {
      RootView()
    }
  }
}
