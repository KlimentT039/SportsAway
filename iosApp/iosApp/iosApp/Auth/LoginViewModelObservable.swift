import Foundation
import Combine
import Shared

@MainActor
final class LoginViewModelObservable: ObservableObject {
  let viewModel: LoginViewModel = KoinHelper.shared.loginViewModel()

  @Published var email: String = ""
  @Published var password: String = ""
  @Published var isEmailValid: Bool = true
  @Published var isPasswordValid: Bool = true
  @Published var wrongCredentials: Bool = false
  @Published var isButtonEnabled: Bool = false
  @Published var isLoading: Bool = false
  @Published var loginFailed: Bool = false
  @Published var loginSucceeded: Bool = false
  @Published var navigateToRegister: Bool = false

  private var stateObserver: FlowObserver?
  private var eventObserver: FlowObserver?

  init() {
    stateObserver = FlowObserver(viewModel.viewState)
    stateObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is LoginViewState.LoginFailed {
        self.loginFailed = true
        self.isLoading = false
      } else if let data = value as? LoginViewState.UserData {
        self.email = data.email
        self.password = data.password
        self.isEmailValid = data.isEmailValid
        self.isPasswordValid = data.isPasswordValid
        self.wrongCredentials = data.wrongCredentials
        self.isButtonEnabled = data.isButtonEnabled
        self.isLoading = data.isLoading
        self.loginFailed = false
      }
    }

    eventObserver = FlowObserver(viewModel.event)
    eventObserver?.watch { [weak self] value in
      guard let self else { return }
      if value is LoginEvents.SuccessfulLogin {
        self.loginSucceeded = true
      } else if value is LoginEvents.NavigateToRegisterActivity {
        self.navigateToRegister = true
      }
    }
  }

  func onEmailChange(_ v: String) { viewModel.onEmailInputChanged(email: v) }
  func onPasswordChange(_ v: String) { viewModel.onPasswordInputChanged(password: v) }
  func onLoginTap() { viewModel.onLoginClick() }
  func onTryAgain() { viewModel.onTryAgainLogin() }
  func onDismissError() { viewModel.onDismissError() }
  func navigateToRegisterRequest() { viewModel.navigateToRegisterActivity() }
}
