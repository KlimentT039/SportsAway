package com.diplomska.sportsaway.feature.events.view.order.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.buttonColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor
import com.diplomska.sportsaway.common.style.compose.typography

@Composable
fun TicketSelectionDialog(
  onDismiss: () -> Unit,
  onSelect: (Int) -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(stringResource(R.string.select_number_of_tickets)) },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        for (num in 1..10) {
          ClickableText(
            text = AnnotatedString("$num"),
            onClick = {
              onSelect(num)
              onDismiss()
            },
            style = typography.mRegular.copy(fontSize = 18.sp)
          )
        }
      }
    },
    confirmButton = {},
    dismissButton = {
      TextButton(
        onClick = onDismiss,
        colors = ButtonDefaults.textButtonColors(
          contentColor = topBarTextColor,
          containerColor = buttonColor
        ),
      ) {
        Text(stringResource(R.string.cancel))
      }
    }
  )
}
