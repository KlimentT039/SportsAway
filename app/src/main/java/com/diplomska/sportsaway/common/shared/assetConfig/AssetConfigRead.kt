package com.diplomska.sportsaway.common.shared.assetConfig

import android.content.Context
import java.io.InputStream

class AssetConfigRead(private val context: Context, val jsonMapper: JsonMapper) {

  fun openAsset(fileName: String): InputStream = context.assets.open(fileName)

  inline operator fun <reified T : Any> invoke(fileName: String): T =
    jsonMapper.parse(openAsset(fileName))
}
