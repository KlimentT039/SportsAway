package com.diplomska.sportsaway.common.shared.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FlowWrapper<T : Any>(private val flow: Flow<T>) {
  private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
  private var job: Job? = null

  fun subscribe(onEach: (T) -> Unit) {
    job?.cancel()
    job = scope.launch { flow.collect { onEach(it) } }
  }

  fun cancel() {
    job?.cancel()
    scope.cancel()
  }
}

fun <T : Any> Flow<T>.wrap(): FlowWrapper<T> = FlowWrapper(this)
