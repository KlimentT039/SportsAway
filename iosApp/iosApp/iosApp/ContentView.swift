import SwiftUI
import Shared

struct ContentView: View {
  var body: some View {
    VStack(spacing: 16) {
      Image(systemName: "checkmark.seal.fill")
        .imageScale(.large)
        .foregroundStyle(.green)
      Text(KoinIosKt.platformGreeting())
        .font(.title3)
        .multilineTextAlignment(.center)
    }
    .padding()
  }
}

#Preview {
  ContentView()
}
