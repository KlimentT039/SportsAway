package com.diplomska.sportsaway.common.style.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault

object Scaffold {

  @Composable
  fun WithTopBarAndBottom(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    containerColor: Color = backgroundDefault
  ) {
    DefaultScaffold(
      topBar = topBar,
      bottomBar = bottomBar,
      content = content,
      containerColor = containerColor
    )
  }

  @Composable
  fun WithTopBarOnly(
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    containerColor: Color = backgroundDefault
  ) {
    DefaultScaffold(
      topBar = topBar,
      content = content,
      containerColor = containerColor
    )
  }

  @Composable
  fun WithBottomBarOnly(
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    containerColor: Color = backgroundDefault
  ) {
    DefaultScaffold(
      bottomBar = bottomBar,
      content = content,
      containerColor = containerColor
    )
  }
}

@Composable
private fun DefaultScaffold(
  topBar: @Composable () -> Unit = {},
  bottomBar: @Composable () -> Unit = {},
  content: @Composable (PaddingValues) -> Unit,
  containerColor: Color = backgroundDefault
) {
  Scaffold(topBar = topBar, bottomBar = bottomBar, containerColor = containerColor, content = {
    Column(Modifier.padding(it).fillMaxWidth()) {
      content(PaddingValues(0.dp))
    }
  })
}