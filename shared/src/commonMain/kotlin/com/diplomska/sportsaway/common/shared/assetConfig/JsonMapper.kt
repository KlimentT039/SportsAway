package com.diplomska.sportsaway.common.shared.assetConfig

import kotlinx.serialization.json.Json

class JsonMapper(val json: Json) {

  inline fun <reified T> parse(text: String): T = json.decodeFromString(text)
}
