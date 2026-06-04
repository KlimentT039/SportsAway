package com.diplomska.sportsaway.common.startup.splashscreen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.backgroundSurface

@Composable
fun SplashScreen() {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundSurface)
  ) {
    Image(
      painter = painterResource(id = R.drawable.ic_logo),
      contentDescription = null
    )
  }
}

@Preview
@Composable
fun SplashScreenPreview() {
  SplashScreen()
}