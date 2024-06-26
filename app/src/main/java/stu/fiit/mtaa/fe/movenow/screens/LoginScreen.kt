package stu.fiit.mtaa.fe.movenow.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.auth.AuthResult
import stu.fiit.mtaa.fe.movenow.auth.AuthUiEvent
import stu.fiit.mtaa.fe.movenow.auth.BiometricPromptManager
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    biometricPromptManager: BiometricPromptManager,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
){

    val viewModel: AuthViewModel = viewModel()
    val windowInfo = rememberWindowInfo()
    val state = viewModel.state
    val context = LocalContext.current
    val wrongCredentials = stringResource(id = R.string.wrongCredentials)

    LaunchedEffect(key1 = viewModel, key2 = context) {
        viewModel.authResults.collect { result ->
            when(result) {
                is AuthResult.Authorized -> {
                    onLoginClick()
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, wrongCredentials,  Toast.LENGTH_LONG).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context, "Unknown error",  Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val biometricResult by biometricPromptManager.promptResults.collectAsState(initial = null)
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Move",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Now",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(90.dp))

            OutlinedTextField(
                value = state.signInUsername,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                label = { Text(stringResource(id = R.string.username), fontSize = 16.sp) },
                placeholder = { Text(stringResource(id = R.string.enterYourUsername)) },
                singleLine = true,
                shape = RoundedCornerShape(32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                    focusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = MaterialTheme.colorScheme.tertiary
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.signInPassword,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                label = { Text(
                    text = stringResource(id = R.string.password),
                    fontSize = 16.sp,
                ) },
                placeholder = { Text(stringResource(id = R.string.enterYourPassword)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                    focusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = MaterialTheme.colorScheme.tertiary
                )
            )

            Spacer(modifier = Modifier.height(66.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                onClick = {
                    viewModel.onEvent(AuthUiEvent.SignIn)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                onClick = {
                    viewModel.onBiometricClick()
                    biometricPromptManager.showBiometricPrompt(
                        title = "Login",
                        description = "Login with your fingerprint",
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.biometricLogin),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            biometricResult?.let { result ->
                when(result) {
                    is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                        result.error
                    }
                    BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                        Toast.makeText(context, "Authentification failed",  Toast.LENGTH_LONG).show()
                    }
                    BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                        "Authentication not set"
                    }
                    BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                        viewModel.onEvent(AuthUiEvent.SignInWBiometric)
                    }
                    BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                        "Feature unavailable"
                    }
                    BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                        "Hardware unavailable"
                    }
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .height(50.dp),
                onClick = onRegisterClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Blue,
                    containerColor = Color.White
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = Color.Blue
                )
            ) {
                Text(
                    text = stringResource(id = R.string.signup),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }else{
        Column(){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Move",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Now",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)){
                    OutlinedTextField(
                        value = state.signInUsername,
                        onValueChange = {
                            viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        label = { Text(stringResource(id = R.string.username), fontSize = 16.sp) },
                        placeholder = { Text(stringResource(id = R.string.enterYourUsername)) },
                        singleLine = true,
                        shape = RoundedCornerShape(32.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                            focusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    OutlinedTextField(
                        value = state.signInPassword,
                        onValueChange = {
                            viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        label = { Text(
                            text = stringResource(id = R.string.password),
                            fontSize = 16.sp,
                        ) },
                        placeholder = { Text(stringResource(id = R.string.enterYourPassword)) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(32.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                            focusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        ),
                        onClick = {
                            viewModel.onEvent(AuthUiEvent.SignIn)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.login),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top=10.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        ),
                        onClick = {
                            viewModel.onBiometricClick()
                            biometricPromptManager.showBiometricPrompt(
                                title = "Login",
                                description = "Login with your fingerprint",
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.biometricLogin),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top=10.dp)
                            .height(50.dp),
                        onClick = onRegisterClick,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Blue,
                            containerColor = Color.White
                        ),
                        border = BorderStroke(
                            width = 2.dp,
                            color = Color.Blue
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.signup),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }


                biometricResult?.let { result ->
                    when(result) {
                        is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                            result.error
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                            Toast.makeText(context, "Authentification failed",  Toast.LENGTH_LONG).show()
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                            "Authentication not set"
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                            viewModel.onEvent(AuthUiEvent.SignInWBiometric)
                        }
                        BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                            "Feature unavailable"
                        }
                        BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                            "Hardware unavailable"
                        }
                    }
                }
            }
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
