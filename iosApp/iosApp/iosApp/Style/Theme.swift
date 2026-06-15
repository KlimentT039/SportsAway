import SwiftUI

enum Theme {
  static let brandGreen = Color(red: 0x01 / 255, green: 0x44 / 255, blue: 0x21 / 255)
  static let accentRed = Color(red: 0xC4 / 255, green: 0x1E / 255, blue: 0x3A / 255)
}

@ViewBuilder
func crestImage(url: String?, name: String? = nil, size: CGFloat) -> some View {
  // football-data.org uses SVG for most team crests and area flags; SwiftUI's
  // AsyncImage can't decode SVG, so for those we render an initials badge instead
  // of a broken-image placeholder.
  let isSvg = url?.lowercased().hasSuffix(".svg") == true
  if let url, let parsed = URL(string: url), !isSvg {
    AsyncImage(url: parsed) { phase in
      switch phase {
      case .success(let image):
        image.resizable().scaledToFit()
      case .empty:
        ProgressView()
      case .failure:
        InitialsBadge(name: name, size: size)
      @unknown default:
        InitialsBadge(name: name, size: size)
      }
    }
    .frame(width: size, height: size)
  } else {
    InitialsBadge(name: name, size: size)
  }
}

struct InitialsBadge: View {
  let name: String?
  let size: CGFloat

  var body: some View {
    Circle()
      .fill(Theme.brandGreen.opacity(0.15))
      .frame(width: size, height: size)
      .overlay(
        Text(initials)
          .font(.system(size: max(10, size * 0.4), weight: .bold))
          .foregroundColor(Theme.brandGreen)
          .lineLimit(1)
          .minimumScaleFactor(0.5)
      )
  }

  private var initials: String {
    let trimmed = (name ?? "").trimmingCharacters(in: .whitespaces)
    guard !trimmed.isEmpty else { return "?" }
    let parts = trimmed.split(separator: " ").prefix(2)
    if parts.count >= 2, let a = parts[0].first, let b = parts[1].first {
      return "\(a)\(b)".uppercased()
    }
    return String(trimmed.prefix(2)).uppercased()
  }
}
