package com.diplomska.sportsaway.common.shared.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AttachSharedHeadersInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val builder: Request.Builder = chain
      .request()
      .newBuilder()
      .addHeader("X-Auth-Token", "6ed4449883e7408f85a146b48f0f8ce0")

    return chain.proceed(builder.build())
  }
}