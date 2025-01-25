package com.diplomska.sportsaway.feature.events.view.order.dialogs.carddialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddCardDialog(
  onDismiss: () -> Unit,
  onSave: (String, String, String, String) -> Unit
) {
  val viewModel = koinViewModel<AddCardViewModel>()
  val errors = viewModel.errors.value
  val outlinedTextColor = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = mainColor)

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(text = stringResource(R.string.add_card_information)) },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Cardholder Name
        OutlinedTextField(
          value = viewModel.cardHolderName,
          onValueChange = { viewModel.cardHolderName = it },
          label = { Text(stringResource(R.string.cardholder_name)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor,
          isError = errors.containsKey("cardHolderName")
        )
        if (errors.containsKey("cardHolderName")) {
          Text(
            text = errors["cardHolderName"]!!,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body2
          )
        }

        // Card Number
        OutlinedTextField(
          value = viewModel.cardNumber,
          onValueChange = { viewModel.cardNumber = it },
          label = { Text(stringResource(R.string.card_number)) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor,
          isError = errors.containsKey("cardNumber")
        )
        if (errors.containsKey("cardNumber")) {
          Text(
            text = errors["cardNumber"]!!,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body2
          )
        }

        // Expiration Date and CVV
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          OutlinedTextField(
            value = viewModel.expirationDate,
            onValueChange = { viewModel.expirationDate = it },
            label = { Text(stringResource(R.string.expiration_date_mm_yy)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            colors = outlinedTextColor,
            isError = errors.containsKey("expirationDate")
          )
          if (errors.containsKey("expirationDate")) {
            Text(
              text = errors["expirationDate"]!!,
              color = MaterialTheme.colors.error,
              style = MaterialTheme.typography.body2
            )
          }

          OutlinedTextField(
            value = viewModel.cvv,
            onValueChange = { viewModel.cvv = it },
            label = { Text(stringResource(R.string.cvv)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            colors = outlinedTextColor,
            isError = errors.containsKey("cvv")
          )
          if (errors.containsKey("cvv")) {
            Text(
              text = errors["cvv"]!!,
              color = MaterialTheme.colors.error,
              style = MaterialTheme.typography.body2
            )
          }
        }
      }
    },
    confirmButton = {
      Button(
        colors = ButtonDefaults.buttonColors(
          backgroundColor = mainColor,
          contentColor = topBarTextColor
        ),
        onClick = {
          if (viewModel.validateFields()) {
            onSave(
              viewModel.cardHolderName,
              viewModel.cardNumber,
              viewModel.expirationDate,
              viewModel.cvv
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
fun AddCardDialogPreview() {
  MaterialTheme {
    AddCardDialog(
      onDismiss = {},
      onSave = { cardHolderName, cardNumber, expirationDate, cvv ->
        println("Preview: $cardHolderName, $cardNumber, $expirationDate")
      }
    )
  }
}
