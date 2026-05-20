package com.diplomska.sportsaway.common.shared.assetConfig

import android.content.Context

class AndroidAssetReader(private val context: Context) : AssetReader {

  override fun read(fileName: String): String =
    context.assets.open(fileName).bufferedReader().use { it.readText() }
}
