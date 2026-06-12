import Foundation
import Shared

/// Wraps a Kotlin `Flow<T>` (e.g. StateFlow) so Swift can subscribe with a closure.
/// Cancels the underlying coroutine on `deinit`.
///
/// Kotlin generics are erased at the K/N boundary, so the closure receives `Any`
/// and the call site is responsible for casting to the concrete type.
final class FlowObserver {
  private let wrapper: FlowWrapper<AnyObject>

  init<F: AnyObject>(_ flow: F) {
    // `flow` is a Kotlin StateFlow/SharedFlow/Flow. The framework header exposes
    // `Kotlinx_coroutines_coreFlow` as a Swift protocol (no generic constraint).
    self.wrapper = FlowWrapper<AnyObject>(flow: flow as! any Kotlinx_coroutines_coreFlow)
  }

  func watch(_ onEach: @escaping (Any) -> Void) {
    wrapper.subscribe { value in onEach(value) }
  }

  deinit {
    wrapper.cancel()
  }
}
