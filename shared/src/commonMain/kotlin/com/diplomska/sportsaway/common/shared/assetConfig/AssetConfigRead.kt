package com.diplomska.sportsaway.common.shared.assetConfig

class AssetConfigRead(val assetReader: AssetReader, val jsonMapper: JsonMapper) {

  inline operator fun <reified T> invoke(fileName: String): T =
    jsonMapper.parse(assetReader.read(fileName))
}
