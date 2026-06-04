package com.diplomska.sportsaway.feature.events.view.order.dialogs.carddialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor
import com.diplomska.sportsaway.feature.events.view.visualtransformation.ExpirationDateVisualTransformation
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddCardDialog(
  onDismiss: () -> Unit,
  onSave: (String, String, String, String) -> Unit
) {
  val viewModel = koinViewModel<AddCardViewModel>()
  val state by viewModel.state.collectAsState()
  val errors = state.errors
  val fieldColors = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = mainColor,
    focusedLabelColor = mainColor
  )

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(text = stringResource(R.string.add_card_information)) },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = state.cardHolderName,
          onValueChange = viewModel::onCardHolderNameChange,
          label = { Text(stringResource(R.string.cardholder_name)) },
          modifier = Modifier.fillMaxWidth(),
          colors = fieldColors,
          isError = errors.containsKey("cardHolderName")
        )
        if (errors.containsKey("cardHolderName")) {
          Text(
            text = errors["cardHolderName"]!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
          )
        }

        OutlinedTextField(
          value = state.cardNumber,
          onValueChange = viewModel::onCardNumberChange,
          label = { Text(stringResource(R.string.card_number)) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          modifier = Modifier.fillMaxWidth(),
          colors = fieldColors,
          isError = errors.containsKey("cardNumber")
        )
        if (errors.containsKey("cardNumber")) {
          Text(
            text = errors["cardNumber"]!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
          )
        }

        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
              value = state.expirationDate,
              onValueChange = viewModel::onExpirationDateChange,
              label = { Text(stringResource(R.string.expiration_date_mm_yy)) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.fillMaxWidth(),
              visualTransformation = ExpirationDateVisualTransformation(),
              colors = fieldColors,
              isError = errors.containsKey("expirationDate")
            )
            if (errors.containsKey("expirationDate")) {
              Text(
                text = errors["expirationDate"]!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
              )
            }
          }

          Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
              value = state.cvv,
              onValueChange = viewModel::onCvvChange,
              label = { Text(stringResource(R.string.cvv)) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.fillMaxWidth(),
              colors = fieldColors,
              isError = errors.containsKey("cvv")
            )
            if (errors.containsKey("cvv")) {
              Text(
                text = errors["cvv"]!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        colors = ButtonDefaults.buttonColors(
          containerColor = mainColor,
          contentColor = topBarTextColor
        ),
        onClick = {
          if (viewModel.validateFields()) {
            val current = viewModel.state.value
            onSave(
              current.cardHolderName,
              current.cardNumber,
              current.expirationDate,
              current.cvv
            )
            onDismiss()
          }
        }
      ) {
        Text(stringResource(R.string.save))
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(stringResource(R.string.cancel), color = mainColor)
      }
    }
  )
}

@Preview(showBackground = true)
@Composable
private fun AddCardDialogPreview() {
  AddCardDialog(
    onDismiss = {},
    onSave = { _, _, _, _ -> }
  )
}
