import SwiftUI
import Shared

struct RegisterView: View {
  @StateObject private var vm = RegisterViewModelObservable()
  let onSuccess: () -> Void

  var body: some View {
    ScrollView {
      VStack(alignment: .leading, spacing: 20) {
        Text("Create account")
          .font(.largeTitle.bold())
          .padding(.top, 32)
        Text("Make a free account to save tickets and teams.")
          .font(.subheadline)
          .foregroundColor(.secondary)

        if vm.isError {
          ErrorBanner(message: "Couldn't create your account. Tap to retry.")
            .onTapGesture { vm.onTryAgain() }
        }

        AuthField(
          title: "Username",
          text: vm.username,
          isError: !vm.isUsernameValid,
          errorText: "Username cannot be empty",
          isSecure: false,
          onChange: vm.onUsernameChange
        )

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

        AuthField(
          title: "Confirm password",
          text: vm.confirmPassword,
          isError: !vm.isConfirmPasswordValid,
          errorText: "Passwords don't match",
          isSecure: true,
          onChange: vm.onConfirmPasswordChange
        )

        Button {
          vm.onSignUpTap()
        } label: {
          HStack {
            if vm.isLoading {
              ProgressView().tint(.white)
            }
            Text("Sign up")
              .font(.headline)
              .foregroundColor(.white)
          }
          .frame(maxWidth: .infinity)
          .padding(.vertical, 14)
          .background(vm.isButtonEnabled ? Theme.brandGreen : Color.gray)
          .clipShape(RoundedRectangle(cornerRadius: 12))
        }
        .disabled(!vm.isButtonEnabled || vm.isLoading)
      }
      .padding(.horizontal, 24)
    }
    .background(Color(.systemBackground))
    .navigationTitle("")
    .onChange(of: vm.registerSucceeded) { _, newValue in
      if newValue { onSuccess() }
    }
  }
}
