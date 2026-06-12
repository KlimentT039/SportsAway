import SwiftUI

enum Theme {
  static let brandGreen = Color(red: 0x01 / 255, green: 0x44 / 255, blue: 0x21 / 255)
  static let accentRed = Color(red: 0xC4 / 255, green: 0x1E / 255, blue: 0x3A / 255)
}

@ViewBuilder
func crestImage(url: String?, size: CGFloat) -> some View {
  if let url, let parsed = URL(string: url) {
    AsyncImage(url: parsed) { image in
      image.resizable().scaledToFit()
    } placeholder: {
      Image(systemName: "shield.fill")
        .resizable().scaledToFit()
        .foregroundStyle(.tertiary)
    }
    .frame(width: size, height: size)
  } else {
    Image(systemName: "shield.fill")
      .resizable().scaledToFit()
      .foregroundStyle(.tertiary)
      .frame(width: size, height: size)
  }
}
