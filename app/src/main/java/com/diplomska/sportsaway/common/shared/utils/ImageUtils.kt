package com.diplomska.sportsaway.common.shared.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.components.ImageWithUrl

@Composable
fun GetImage(imageRes: String?, pictureSize: Int = 40) = run {
  if (!imageRes.isNullOrEmpty()) {
    ImageWithUrl(imageRes, pictureSize)
  } else {
    Image(
      painter = painterResource(id = R.drawable.ic_generic_club),
      contentDescription = null,
      modifier = Modifier.size(pictureSize.dp)
    )
  }
}