package com.diplomska.sportsaway.feature.events.view.order.dialogs

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

@Composable
fun BillingAddressDialog(
  onDismiss: () -> Unit,
  onSave: (String, String, String, String, String, String) -> Unit
) {
  var fullName by remember { mutableStateOf("") }
  var addressLine1 by remember { mutableStateOf("") }
  var addressLine2 by remember { mutableStateOf("") }
  var city by remember { mutableStateOf("") }
  var country by remember { mutableStateOf("") }
  var zipCode by remember { mutableStateOf("") }

  val outlinedTextColor = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = mainColor)


  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(text = "Billing Address") },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = fullName,
          onValueChange = { fullName = it },
          label = { Text(stringResource(R.string.full_name)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )
        OutlinedTextField(
          value = addressLine1,
          onValueChange = { addressLine1 = it },
          label = { Text(stringResource(R.string.address_line_1)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )
        OutlinedTextField(
          value = addressLine2,
          onValueChange = { addressLine2 = it },
          label = { Text(stringResource(R.string.address_line_2_optional)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )
        OutlinedTextField(
          value = city,
          onValueChange = { city = it },
          label = { Text(stringResource(R.string.city)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )
        OutlinedTextField(
          value = country,
          onValueChange = { country = it },
          label = { Text(stringResource(R.string.country)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )
        OutlinedTextField(
          value = zipCode,
          onValueChange = { zipCode = it },
          label = { Text(stringResource(R.string.zip_code)) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )
      }
    },
    confirmButton = {
      Button(colors = ButtonDefaults.buttonColors(
        backgroundColor = mainColor,
        contentColor = topBarTextColor
      ),
        onClick = {
          onSave(fullName, addressLine1, addressLine2, city, zipCode, country)
          onDismiss()
        }) {
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
        // Print or debug the entered data (for preview purposes)
        println("Preview: $fullName, $addressLine1, $addressLine2, $city, $zipCode. $country")
      }
    )
  }
}