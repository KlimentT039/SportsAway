package com.diplomska.sportsaway.common.shared.assetConfig

import kotlinx.serialization.json.Json
import java.io.InputStream

class JsonMapper(val json: Json) {

  inline fun <reified T> parse(text: String): T = json.decodeFromString(text)

  inline fun <reified T> parse(stream: InputStream): T =
    stream.bufferedReader().use { json.decodeFromString<T>(it.readText()) }
}
