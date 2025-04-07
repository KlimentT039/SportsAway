package com.diplomska.sportsaway.feature.authentication.register.view

import ErrorScreen
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextPrimary
import com.diplomska.sportsaway.feature.authentication.login.view.LoginActivity
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateUserScreen() {
  val viewModel = koinViewModel<RegisterViewModel>()
  val uiState = viewModel.viewState.collectAsState().value
  when (uiState) {
    is RegisterViewState.Loading -> OverlayLoader()
    is RegisterViewState.Error -> ErrorScreen(
      title = stringResource(R.string.something_went_wrong),
      description = "User cannot be created at the moment",
      onClick = viewModel::onTryAgainClick
    )

    is RegisterViewState.UserData ->
      CreateUserContent(
        onEmailInputChanged = viewModel::onEmailInputChanged,
        onUsernameInputChanged = viewModel::onUsernameInputChanged,
        onPasswordInputChanged = viewModel::onPasswordInputChanged,
        onSignupButtonClicked = viewModel::onSignUpClick
      )
  }
}

@Composable
private fun CreateUserContent(
  onEmailInputChanged: (String) -> Unit,
  onPasswordInputChanged: (String) -> Unit,
  onUsernameInputChanged: (String) -> Unit,
  onSignupButtonClicked: () -> Unit
) {
  val context = LocalContext.current
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var confirmPassword by remember { mutableStateOf("") }
  var username by remember { mutableStateOf("") }

  val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

  Scaffold.WithTopBarOnly(
    topBar = {
      TopAppBar(
        title = {},
        navigationIcon = {
          IconButton(onClick = { backDispatcher?.onBackPressed() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
          }
        },
        backgroundColor = backgroundDefault,
        elevation = 0.dp
      )
    },
    content = {

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
          onValueChange = {
            email = it
            onEmailInputChanged(it)
          },
          modifier = Modifier
            .fillMaxWidth(),
          label = { Text(stringResource(id = R.string.email)) },
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
          ),
          colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundDefault,
            focusedLabelColor = mainColor,
            focusedIndicatorColor = mainColor,
            cursorColor = typographyTextPrimary
          )
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
          value = password,
          onValueChange = {
            password = it
            onPasswordInputChanged(it)
          },
          modifier = Modifier
            .fillMaxWidth(),
          label = { Text(stringResource(id = R.string.password)) },
          visualTransformation = PasswordVisualTransformation(),
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
          ),
          colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundDefault,
            focusedLabelColor = mainColor,
            focusedIndicatorColor = mainColor,
            cursorColor = typographyTextPrimary
          )
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
          value = confirmPassword,
          onValueChange = { confirmPassword = it },
          modifier = Modifier
            .fillMaxWidth(),
          label = { Text(stringResource(id = R.string.confirm_password)) },
          visualTransformation = PasswordVisualTransformation(),
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
          ),
          colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundDefault,
            focusedLabelColor = mainColor,
            focusedIndicatorColor = mainColor,
            cursorColor = typographyTextPrimary
          )
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
          value = username,
          onValueChange = {
            username = it
            onUsernameInputChanged(it)
          },
          modifier = Modifier
            .fillMaxWidth(),
          label = { Text(stringResource(id = R.string.username)) },
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
          ),
          colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundDefault,
            focusedLabelColor = mainColor,
            focusedIndicatorColor = mainColor,
            cursorColor = typographyTextPrimary
          )
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
          onClick = { onSignupButtonClicked() },
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

        Text(
          stringResource(id = R.string.have_account),
          color = mainColor,
          modifier = Modifier.clickable { context.startActivity(LoginActivity.createIntent(context)) })
      }
    })
}

@Preview
@Composable
private fun PreviewCreateUserScreen() {
  CreateUserContent(
    onEmailInputChanged = {},
    onPasswordInputChanged = {},
    onUsernameInputChanged = {},
    onSignupButtonClicked = {}
  )
}
