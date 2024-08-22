package com.diplomska.sportsaway.common.shared.assetConfig

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream

/**
 * Helper class to map objects from JSON streams to respective classes or [JsonNode].
 */
class JsonMapper(private val objectMapper: ObjectMapper) {

  fun <T> parse(json: String, clazz: Class<T>): T = objectMapper.readValue(json, clazz)
  fun <T> parse(stream: InputStream, clazz: Class<T>): T = objectMapper.readValue(stream, clazz)
  fun <T> parse(json: String, typeRef: TypeReference<T>): T = objectMapper.readValue(json, typeRef)
  fun <T> parse(jsonNode: JsonNode, clazz: Class<T>): T = ObjectMapper().treeToValue(jsonNode, clazz)

  fun parse(stream: InputStream): JsonNode = objectMapper.readTree(stream)
  fun parse(text: String): JsonNode = objectMapper.readTree(text)

}