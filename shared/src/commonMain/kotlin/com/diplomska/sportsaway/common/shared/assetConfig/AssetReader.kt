package com.diplomska.sportsaway.common.shared.assetConfig

interface AssetReader {
  fun read(fileName: String): String
}
