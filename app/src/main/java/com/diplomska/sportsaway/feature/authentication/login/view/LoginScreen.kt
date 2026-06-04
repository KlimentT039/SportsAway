package com.diplomska.sportsaway.feature.authentication.login.view

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextPrimary
import com.diplomska.sportsaway.common.style.compose.typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen() {
  val viewModel = koinViewModel<LoginViewModel>()
  val uiState = viewModel.viewState.collectAsState()
  when (val state = uiState.value) {
    is LoginViewState.LoginFailed -> ErrorScreen(
      title = stringResource(R.string.login_failed),
      description = stringResource(R.string.please_try_again),
      onClick = viewModel::onTryAgainLogin
    )

    is LoginViewState.UserData -> LoginContent(
      uiState = state,
      onEmailInputChanged = viewModel::onEmailInputChanged,
      onPasswordInputChanged = viewModel::onPasswordInputChanged,
      onLoginButtonClicked = viewModel::onLoginClick,
      onSignUpClicked = viewModel::navigateToRegisterActivity,
      onDismissError = viewModel::onDismissError
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent(
  uiState: LoginViewState.UserData,
  onEmailInputChanged: (String) -> Unit,
  onPasswordInputChanged: (String) -> Unit,
  onLoginButtonClicked: () -> Unit,
  onSignUpClicked: () -> Unit,
  onDismissError: () -> Unit,
) {
  val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
  val fieldColors = TextFieldDefaults.colors(
    focusedContainerColor = backgroundDefault,
    unfocusedContainerColor = backgroundDefault,
    focusedLabelColor = mainColor,
    focusedIndicatorColor = mainColor,
    cursorColor = typographyTextPrimary
  )

  Scaffold.WithTopBarOnly(
    topBar = {
      TopAppBar(
        title = {},
        navigationIcon = {
          IconButton(onClick = { backDispatcher?.onBackPressed() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.generic_back))
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundDefault)
      )
    },
    content = {
      if (uiState.isLoading) {
        OverlayLoader()
      }
      val focusRequester = remember { FocusRequester() }
      Column(
        modifier = Modifier
          .fillMaxSize()
          .background(backgroundDefault)
          .imePadding()
          .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        if (uiState.wrongCredentials) {
          AlertDialog(
            onDismissRequest = onDismissError,
            title = { Text(stringResource(R.string.login_failed)) },
            text = { Text(stringResource(R.string.wrong_credentials)) },
            confirmButton = {
              TextButton(onClick = onDismissError) {
                Text(stringResource(R.string.generic_ok), color = mainColor)
              }
            }
          )
        }
        Image(
          modifier = Modifier.padding(top = 100.dp),
          painter = painterResource(id = R.drawable.ic_logo),
          contentDescription = stringResource(R.string.generic_logo),
          contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
          value = uiState.email,
          onValueChange = onEmailInputChanged,
          isError = !uiState.isEmailValid,
          modifier = Modifier.fillMaxWidth(),
          label = { Text(stringResource(id = R.string.email)) },
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
          ),
          keyboardActions = KeyboardActions(
            onNext = { focusRequester.requestFocus() }
          ),
          colors = fieldColors
        )

        if (!uiState.isEmailValid) {
          Spacer(Modifier.height(10.dp))
          Text(
            stringResource(R.string.invalid_email_format),
            style = typography.xsRegular.copy(color = Color.Red)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
          value = uiState.password,
          onValueChange = onPasswordInputChanged,
          isError = !uiState.isPasswordValid,
          visualTransformation = PasswordVisualTransformation(),
          modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
          label = { Text(stringResource(id = R.string.password)) },
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
          ),
          colors = fieldColors
        )

        if (!uiState.isPasswordValid) {
          Text(
            text = stringResource(R.string.invalid_password),
            style = typography.xsRegular.copy(color = Color.Red),
            textAlign = TextAlign.Center
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
          text = stringResource(id = R.string.no_account),
          color = mainColor,
          modifier = Modifier.clickable { onSignUpClicked() }
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
          onClick = onLoginButtonClicked,
          enabled = uiState.isButtonEnabled,
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = mainColor,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
          )
        ) {
          Text(text = stringResource(R.string.log_in))
        }
        Spacer(modifier = Modifier.height(30.dp))
      }
    }
  )
}

@Preview
@Composable
private fun PreviewLoginScreen() {
  LoginContent(
    onEmailInputChanged = { },
    onPasswordInputChanged = { },
    onLoginButtonClicked = { },
    uiState = LoginViewState.UserData(),
    onSignUpClicked = {},
    onDismissError = {}
  )
}
