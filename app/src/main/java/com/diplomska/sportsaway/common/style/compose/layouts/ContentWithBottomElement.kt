package com.diplomska.sportsaway.common.style.compose.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContentWithBottomElement(
  modifier: Modifier = Modifier,
  topContent: @Composable ColumnScope.() -> Unit,
  bottomContent: @Composable ColumnScope.() -> Unit,
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    topContent()
    Box(Modifier.weight(1f))
    bottomContent()
  }
}