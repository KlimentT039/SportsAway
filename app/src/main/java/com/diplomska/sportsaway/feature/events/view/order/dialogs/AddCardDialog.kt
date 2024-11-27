import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor

@Composable
fun AddCardDialog(onDismiss: () -> Unit, onSave: (String, String, String) -> Unit) {
  var cardHolderName by remember { mutableStateOf("") }
  var cardNumber by remember { mutableStateOf("") }
  var expirationDate by remember { mutableStateOf("") }
  var cvv by remember { mutableStateOf("") }

  val outlinedTextColor = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = mainColor)

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(text = stringResource(R.string.add_card_information)) },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = cardHolderName,
          onValueChange = { cardHolderName = it },
          label = { Text(stringResource(R.string.cardholder_name)) },
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )
        OutlinedTextField(
          value = cardNumber,
          onValueChange = { cardNumber = it },
          label = { Text(stringResource(R.string.card_number)) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          visualTransformation = VisualTransformation.None,
          modifier = Modifier.fillMaxWidth(),
          colors = outlinedTextColor
        )
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          OutlinedTextField(
            value = expirationDate,
            onValueChange = { expirationDate = it },
            label = { Text(stringResource(R.string.expiration_date_mm_yy)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            colors = outlinedTextColor
          )
          OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text(stringResource(R.string.cvv)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation.None,
            modifier = Modifier.weight(1f),
            colors = outlinedTextColor
          )
        }
      }
    },
    confirmButton = {
      Button(
        colors = ButtonDefaults.buttonColors(
          backgroundColor = mainColor,
          contentColor = topBarTextColor,
        ),
        onClick = {
          onSave(cardHolderName, cardNumber, expirationDate)
          onDismiss()
        }) {
        Text("Save")
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
      onSave = { cardHolderName, cardNumber, expirationDate ->
        println("Preview: $cardHolderName, $cardNumber, $expirationDate")
      }
    )
  }
}
