package com.diplomska.sportsaway.feature.events.view.order.dialogs.billingaddress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun BillingAddressDialog(
  onDismiss: () -> Unit,
  onSave: (String, String, String, String, String, String) -> Unit
) {
  val viewModel = koinViewModel<BillingAddressViewModel>()
  val state by viewModel.state.collectAsState()
  val errors = state.errors
  val outlinedTextColor =
    TextFieldDefaults.colors(focusedIndicatorColor = mainColor, focusedTextColor = mainColor)

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(text = "Billing Address") },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = state.fullName,
          onValueChange = viewModel::onFullNameChange,
          label = { Text(stringResource(R.string.full_name)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor,
          isError = errors.containsKey("fullName")
        )
        if (errors.containsKey("fullName")) {
          Text(
            text = errors["fullName"]!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
          )
        }

        OutlinedTextField(
          value = state.addressLine1,
          onValueChange = viewModel::onAddressLine1Change,
          label = { Text(stringResource(R.string.address_line_1)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor,
          isError = errors.containsKey("addressLine1")
        )
        if (errors.containsKey("addressLine1")) {
          Text(
            text = errors["addressLine1"]!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
          )
        }

        OutlinedTextField(
          value = state.addressLine2,
          onValueChange = viewModel::onAddressLine2Change,
          label = { Text(stringResource(R.string.address_line_2_optional)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )

        OutlinedTextField(
          value = state.city,
          onValueChange = viewModel::onCityChange,
          label = { Text(stringResource(R.string.city)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor,
          isError = errors.containsKey("city")
        )
        if (errors.containsKey("city")) {
          Text(
            text = errors["city"]!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
          )
        }

        OutlinedTextField(
          value = state.country,
          onValueChange = viewModel::onCountryChange,
          label = { Text(stringResource(R.string.country)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor,
          isError = errors.containsKey("country")
        )
        if (errors.containsKey("country")) {
          Text(
            text = errors["country"]!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
          )
        }

        OutlinedTextField(
          value = state.zipCode,
          onValueChange = viewModel::onZipCodeChange,
          label = { Text(stringResource(R.string.zip_code)) },
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
          ),
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor,
          isError = errors.containsKey("zipCode")
        )
        if (errors.containsKey("zipCode")) {
          Text(
            text = errors["zipCode"]!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
          )
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
              current.fullName,
              current.addressLine1,
              current.addressLine2,
              current.city,
              current.zipCode,
              current.country
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


@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun BillingAddressDialogPreview() {
  MaterialTheme {
    BillingAddressDialog(
      onDismiss = {},
      onSave = { fullName, addressLine1, addressLine2, city, zipCode, country ->
        println("Preview: $fullName, $addressLine1, $addressLine2, $city, $zipCode. $country")
      }
    )
  }
}
