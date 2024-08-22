package com.diplomska.sportsaway.common.style.compose.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun ImageWithUrl(url: String, size: Int) {
  if (!url.endsWith(".svg")) {
    AsyncImage(
      model = url,
      contentDescription = null,
      modifier = Modifier.size(size.dp)
    )
  }else{
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .decoderFactory(SvgDecoder.Factory())
        .build(),
      contentDescription = null,
      modifier = Modifier.size(size.dp)
    )
  }
}