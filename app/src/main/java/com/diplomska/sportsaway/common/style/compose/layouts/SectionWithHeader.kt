package com.diplomska.sportsaway.common.style.compose.layouts

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.common.style.compose.components.Section
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.typography

@Composable
fun SectionWithHeader(
  @StringRes title: Int,
  modifier: Modifier = Modifier,
  @StringRes endWord: Int? = null,
  titleStyle: TextStyle = typography.mRegular,
  content: @Composable () -> Unit
) {
  Column(modifier.background(backgroundDefault)) {
    Row {
      Text(
        text = stringResource(title),
        style = titleStyle,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
      )
      Spacer(modifier = Modifier.weight(1f))

      endWord?.let {
        Text(
          text = stringResource(it),
          style = typography.xsRegular,
          modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
        )
      }
    }
    Section(Modifier.fillMaxWidth()) {
      content()
    }
  }
}