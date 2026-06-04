package com.diplomska.sportsaway.common.style.compose.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.diplomska.sportsaway.R

@Composable
fun OverlayLoader() {
  Loader()
}

@Composable
fun Loader(){
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(R.color.backgroundSurface))
      .pointerInput(Unit) {
        // Consume pointer events to prevent interaction with buttons below
        // This prevents clicks from reaching the buttons below the overlay
      },
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator(
      color = Color.Green
    )
  }
}

@Preview
@Composable
private fun OverlayLoaderPreview() {
  OverlayLoader()
}
