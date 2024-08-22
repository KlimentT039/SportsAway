package com.diplomska.sportsaway.common.shared.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Creates HTTP client Builder.
 *
 * @return [OkHttpClient] The HTTP client Builder.
 */
fun getCoreHttpBuilder(): OkHttpClient.Builder {
  val httpClient = OkHttpClient.Builder().addInterceptor(AttachSharedHeadersInterceptor())
  setLoggingInterceptor(httpClient)

  return httpClient
}

private fun setLoggingInterceptor(httpClient: OkHttpClient.Builder) {
  val loggingInterceptor = HttpLoggingInterceptor()
  loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
  httpClient.addInterceptor(loggingInterceptor)
}