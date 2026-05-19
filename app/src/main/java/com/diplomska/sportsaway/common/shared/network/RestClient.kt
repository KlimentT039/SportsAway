package com.diplomska.sportsaway.common.shared.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.java.KoinJavaComponent
import retrofit2.Retrofit

object RestClient {

  private const val FOOTBALL_BASE_URL = "https://api.football-data.org"

  private val httpClient: OkHttpClient by KoinJavaComponent.inject(OkHttpClient::class.java)
  private val json: Json by KoinJavaComponent.inject(Json::class.java)

  fun <T> createService(serviceClass: Class<T>, baseUrl: String = FOOTBALL_BASE_URL): T {
    return createRetrofitInstance(baseUrl).create(serviceClass)
  }

  private fun createRetrofitInstance(baseUrl: String): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(httpClient)
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .build()
  }
}
