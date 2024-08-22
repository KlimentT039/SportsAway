package com.diplomska.sportsaway.common.shared.network

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.koin.java.KoinJavaComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RestClient {

  private const val BASE_URL = "https://api.football-data.org"

  private val httpClient: OkHttpClient by KoinJavaComponent.inject(OkHttpClient::class.java)

  private val retrofit: Retrofit by lazy {
    try {
      Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(createConverter())
        .build()
    } catch (e: Exception) {
      // Handle exception, e.g., log an error
      throw RuntimeException("Error creating Retrofit instance", e)
    }
  }

  fun <T> createService(serviceClass: Class<T>): T {
    return retrofit.create(serviceClass)
  }

  private fun createConverter(): JacksonConverterFactory {
    val objectMapper = ObjectMapper()
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    return JacksonConverterFactory.create(objectMapper)
  }
}