import Foundation
import Shared

@MainActor
final class RegisterViewModelObservable: ObservableObject {
  let viewModel: RegisterViewModel = KoinHelper.shared.registerViewModel()

  @Published var isLoading: Bool = false
  @Published var isError: Bool = false
  @Published var email: String = ""
  @Published var password: String = ""
  @Published var username: String = ""
  @Published var confirmPassword: String = ""
  @Published var isUsernameValid: Bool = true
  @Published var isPasswordValid: Bool = true
  @Published var isEmailValid: Bool = true
  @Published var isConfirmPasswordValid: Bool = true
  @Published var isButtonEnabled: Bool = false
  @Published var registerSucceeded: Bool = false

  private var stateObserver: FlowObserver?
  private var eventObserver: FlowObserver?

  init() {
    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is RegisterViewStateLoading {
        self.isLoading = true
        self.isError = false
      } else if value is RegisterViewStateError {
        self.isLoading = false
        self.isError = true
      } else if let data = value as? RegisterViewStateUserData {
        self.isLoading = false
        self.isError = false
        self.email = data.email
        self.password = data.password
        self.username = data.username
        self.confirmPassword = data.confirmPassword
        self.isUsernameValid = data.isUsernameValid
        self.isPasswordValid = data.isPasswordValid
        self.isEmailValid = data.isEmailValid
        self.isConfirmPasswordValid = data.isConfirmPasswordValid
        self.isButtonEnabled = data.isButtonEnabled
      }
    }

    eventObserver = FlowObserver(viewModel.event)
    eventObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is RegisterEventNavigateToDashboard {
        self.registerSucceeded = true
      }
    }
  }

  func onEmailChange(_ v: String) { viewModel.onEmailInputChanged(email: v) }
  func onPasswordChange(_ v: String) { viewModel.onPasswordInputChanged(password: v) }
  func onUsernameChange(_ v: String) { viewModel.onUsernameInputChanged(username: v) }
  func onConfirmPasswordChange(_ v: String) { viewModel.onConfirmPasswordInputChanged(confirmPassword: v) }
  func onSignUpTap() { viewModel.onSignUpClick() }
  func onTryAgain() { viewModel.onTryAgainClick() }
}
