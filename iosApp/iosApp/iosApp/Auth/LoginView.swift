import SwiftUI
import Shared

struct LoginView: View {
  @StateObject private var vm = LoginViewModelObservable()
  @State private var path: [AuthRoute] = []
  let onSuccess: () -> Void
  let onCancel: (() -> Void)?

  var body: some View {
    NavigationStack(path: $path) {
      ScrollView {
        VStack(alignment: .leading, spacing: 20) {
          Text("Welcome back")
            .font(.largeTitle.bold())
            .padding(.top, 32)
          Text("Sign in to follow your teams and book tickets.")
            .font(.subheadline)
            .foregroundColor(.secondary)

          AuthField(
            title: "Email",
            text: vm.email,
            isError: !vm.isEmailValid,
            errorText: "Enter a valid email",
            isSecure: false,
            onChange: vm.onEmailChange
          )
          AuthField(
            title: "Password",
            text: vm.password,
            isError: !vm.isPasswordValid,
            errorText: "Password must be at least 6 characters",
            isSecure: true,
            onChange: vm.onPasswordChange
          )

          if vm.wrongCredentials {
            HStack {
              Image(systemName: "exclamationmark.circle.fill").foregroundColor(Theme.accentRed)
              Text("Wrong credentials. Try again.")
                .font(.caption)
                .foregroundColor(Theme.accentRed)
              Spacer()
              Button("Dismiss") { vm.onDismissError() }
                .font(.caption)
            }
          }

          if vm.loginFailed {
            ErrorBanner(message: "Login failed. Tap to retry.")
              .onTapGesture { vm.onTryAgain() }
          }

          Button {
            vm.onLoginTap()
          } label: {
            HStack {
              if vm.isLoading {
                ProgressView().tint(.white)
              }
              Text("Sign in")
                .font(.headline)
                .foregroundColor(.white)
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 14)
            .background(vm.isButtonEnabled ? Theme.brandGreen : Color.gray)
            .clipShape(RoundedRectangle(cornerRadius: 12))
          }
          .disabled(!vm.isButtonEnabled || vm.isLoading)

          HStack {
            Text("New here?").foregroundColor(.secondary)
            Button("Create account") {
              vm.navigateToRegisterRequest()
            }
            .foregroundColor(Theme.brandGreen)
            Spacer()
          }
          .font(.subheadline)
        }
        .padding(.horizontal, 24)
      }
      .background(Color(.systemBackground))
      .navigationTitle("")
      .toolbar {
        if let onCancel {
          ToolbarItem(placement: .topBarLeading) {
            Button("Cancel") { onCancel() }
          }
        }
      }
      .navigationDestination(for: AuthRoute.self) { route in
        switch route {
        case .register:
          RegisterView(onSuccess: onSuccess)
        }
      }
      .onChange(of: vm.navigateToRegister) { _, newValue in
        if newValue {
          path.append(.register)
          vm.navigateToRegister = false
        }
      }
      .onChange(of: vm.loginSucceeded) { _, newValue in
        if newValue { onSuccess() }
      }
    }
  }
}

struct AuthField: View {
  let title: String
  let text: String
  let isError: Bool
  let errorText: String
  let isSecure: Bool
  let onChange: (String) -> Void

  @State private var local: String = ""

  var body: some View {
    VStack(alignment: .leading, spacing: 4) {
      Text(title)
        .font(.caption.weight(.semibold))
        .foregroundColor(.secondary)
      Group {
        if isSecure {
          SecureField(title, text: $local)
        } else {
          TextField(title, text: $local)
            .textInputAutocapitalization(.never)
            .autocorrectionDisabled()
            .keyboardType(title.lowercased().contains("email") ? .emailAddress : .default)
        }
      }
      .padding(12)
      .background(Color(.systemGroupedBackground))
      .clipShape(RoundedRectangle(cornerRadius: 10))
      .overlay(
        RoundedRectangle(cornerRadius: 10)
          .stroke(isError ? Theme.accentRed : Color.clear, lineWidth: 1)
      )
      if isError {
        Text(errorText)
          .font(.caption2)
          .foregroundColor(Theme.accentRed)
      }
    }
    .onAppear { local = text }
    .onChange(of: local) { _, newValue in onChange(newValue) }
    .onChange(of: text) { _, newValue in
      if newValue != local { local = newValue }
    }
  }
}
