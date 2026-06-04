package com.diplomska.sportsaway.feature.events.view.order.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextPrimary
import com.diplomska.sportsaway.common.style.compose.typography

@Composable
fun OrderSuccessDialog(
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = stringResource(R.string.order_successful),
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        color = typographyTextPrimary
      )
    },
    text = {
      Text(
        text = stringResource(R.string.your_order_has_been_placed_successfully),
        style = typography.sRegularSecundary,
      )
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = mainColor),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = stringResource(R.string.generic_contiunue),
          color = Color.White,
          fontWeight = FontWeight.Bold
        )
      }
    }
  )
}

@Preview
@Composable
private fun OrderSuccessDialogPreview() {
  OrderSuccessDialog({})
}
