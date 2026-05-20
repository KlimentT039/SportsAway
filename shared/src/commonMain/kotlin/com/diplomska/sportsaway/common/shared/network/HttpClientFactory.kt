package com.diplomska.sportsaway.common.shared.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val FOOTBALL_AUTH_TOKEN = "6ed4449883e7408f85a146b48f0f8ce0"
private const val SPORTS_DB_API_KEY = "60130162"

fun createHttpClient(engine: HttpClientEngine, json: Json): HttpClient =
  HttpClient(engine) {
    install(ContentNegotiation) {
      json(json)
    }
    install(Logging) {
      level = LogLevel.BODY
    }
    install(DefaultRequest) {
      header("X-Auth-Token", FOOTBALL_AUTH_TOKEN)
      header("X-API-KEY", SPORTS_DB_API_KEY)
    }
  }

expect fun httpClientEngine(): HttpClientEngine
