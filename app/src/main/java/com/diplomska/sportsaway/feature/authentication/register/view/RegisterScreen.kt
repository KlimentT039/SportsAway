package com.diplomska.sportsaway.feature.authentication.register.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.mainColor

@Composable
fun CreateUserScreen() {
  CreateUserContent()
}

@Composable
fun CreateUserContent() {
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var confirmPassword by remember { mutableStateOf("") }
  var username by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundDefault)
      .padding(horizontal = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
      modifier = Modifier.padding(top = 100.dp),
      painter = painterResource(id = R.drawable.ic_logo),
      contentDescription = "Logo",
      contentScale = ContentScale.Fit
    )
    Spacer(modifier = Modifier.height(30.dp))
    TextField(
      value = email,
      onValueChange = { email = it },
      modifier = Modifier
        .fillMaxWidth(),
      label = { Text(stringResource(id = R.string.email)) },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
      ),
      keyboardActions = KeyboardActions(
        onNext = { }
      ),
      colors = TextFieldDefaults.textFieldColors(backgroundColor = backgroundDefault)
    )

    Spacer(modifier = Modifier.height(16.dp))
    TextField(
      value = password,
      onValueChange = { password = it },
      modifier = Modifier
        .fillMaxWidth(),
      label = { Text(stringResource(id = R.string.password)) },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
      ),
      keyboardActions = KeyboardActions(
        onNext = { }
      ),
      colors = TextFieldDefaults.textFieldColors(backgroundColor = backgroundDefault)
    )

    Spacer(modifier = Modifier.height(16.dp))
    TextField(
      value = confirmPassword,
      onValueChange = { confirmPassword = it },
      modifier = Modifier
        .fillMaxWidth(),
      label = { Text(stringResource(id = R.string.confirm_password)) },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
      ),
      keyboardActions = KeyboardActions(
        onDone = { /* Perform action when done typing */ }
      ),
      colors = TextFieldDefaults.textFieldColors(backgroundColor = backgroundDefault)
    )

    Spacer(modifier = Modifier.height(16.dp))
    TextField(
      value = confirmPassword,
      onValueChange = { confirmPassword = it },
      modifier = Modifier
        .fillMaxWidth(),
      label = { Text(stringResource(id = R.string.username)) },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
      ),
      keyboardActions = KeyboardActions(
        onDone = { /* Perform action when done typing */ }
      ),
      colors = TextFieldDefaults.textFieldColors(backgroundColor = backgroundDefault)
    )

    Spacer(modifier = Modifier.height(40.dp))

    Button(
      onClick = { /* Perform action for creating user */ },
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
      colors = ButtonDefaults.textButtonColors(
        backgroundColor = mainColor,
        contentColor = Color.White
      )
    ) {
      Text(text = "Create User")
    }

    Spacer(modifier = Modifier.height(30.dp))

    Text(stringResource(id = R.string.have_account), color = mainColor)
  }
}

@Preview
@Composable
fun PreviewCreateUserScreen() {
  CreateUserScreen()
}
