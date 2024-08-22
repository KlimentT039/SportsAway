package com.diplomska.sportsaway.common.shared.assetConfig

import android.content.Context
import kotlin.reflect.KClass

class AssetConfigRead(private val context: Context, private val jsonMapper: JsonMapper) {

  operator fun <T : Any> invoke(fileName: String, configType: KClass<T>): T {
    val file = context.assets.open(fileName)
    return jsonMapper.parse(file, configType.java)
  }

}
