package com.diplomska.sportsaway.common.shared.assetConfig

import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

class IosAssetReader : AssetReader {

  override fun read(fileName: String): String {
    val name = fileName.substringBeforeLast(".")
    val extension = fileName.substringAfterLast(".", "")
    val path = NSBundle.mainBundle.pathForResource(name, extension)
      ?: error("Resource not found in main bundle: $fileName")
    return NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null)
      ?: error("Could not read resource: $fileName")
  }
}
