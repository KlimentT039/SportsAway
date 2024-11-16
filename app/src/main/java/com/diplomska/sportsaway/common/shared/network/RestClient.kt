package com.diplomska.sportsaway.common.shared.network

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.koin.java.KoinJavaComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RestClient {

  private const val FOOTBALL_BASE_URL = "https://api.football-data.org"

  private val httpClient: OkHttpClient by KoinJavaComponent.inject(OkHttpClient::class.java)


  fun <T> createService(serviceClass: Class<T>, baseUrl: String = FOOTBALL_BASE_URL): T {
    return createRetrofitInstance(baseUrl).create(serviceClass)
  }

  private fun createRetrofitInstance(baseUrl: String): Retrofit {
    return try {
      Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(createConverter())
        .build()
    } catch (e: Exception) {
      // Handle exception, e.g., log an error
      throw RuntimeException("Error creating Retrofit instance", e)
    }
  }

  private fun createConverter(): JacksonConverterFactory {
    val objectMapper = ObjectMapper()
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    return JacksonConverterFactory.create(objectMapper)
  }
}