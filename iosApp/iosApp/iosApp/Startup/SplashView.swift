import SwiftUI

struct SplashView: View {
  var body: some View {
    ZStack {
      Theme.brandGreen.ignoresSafeArea()
      VStack(spacing: 16) {
        Image(systemName: "sportscourt.fill")
          .resizable()
          .scaledToFit()
          .frame(width: 96, height: 96)
          .foregroundColor(.white)
        Text("SportsAway")
          .font(.largeTitle.bold())
          .foregroundColor(.white)
        ProgressView()
          .tint(.white)
          .padding(.top, 24)
      }
    }
  }
}

#Preview {
  SplashView()
}
