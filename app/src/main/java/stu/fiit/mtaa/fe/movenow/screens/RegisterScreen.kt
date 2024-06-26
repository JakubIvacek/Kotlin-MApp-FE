package stu.fiit.mtaa.fe.movenow.screens

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.auth.AuthResult
import stu.fiit.mtaa.fe.movenow.auth.AuthUiEvent
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.viewmodels.AuthViewModel


@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit
){

    val viewModel: AuthViewModel = viewModel()
    val state = viewModel.state
    val context = LocalContext.current
    val windowInfo = rememberWindowInfo()
    LaunchedEffect(key1 = viewModel, key2 = context) {
        viewModel.authResults.collect { result ->
            when(result) {
                is AuthResult.Authorized -> {
                    onRegisterClick()
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, "This username is already taken",  Toast.LENGTH_LONG).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context, "Unknown error",  Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))

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

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = state.registerUsername,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.RegisterUsernameChanged(it))
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
                value = state.registerPassword,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.RegisterPasswordChanged(it))
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

            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = state.registerFullName,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.RegisterFullNameChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                label = { Text(stringResource(id = R.string.fullName), fontSize = 16.sp) },
                placeholder = { Text(stringResource(id = R.string.enterfullName)) },
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
                value = state.registerAge,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.RegisterAgeChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                label = { Text(stringResource(id = R.string.age), fontSize = 16.sp) },
                placeholder = { Text(stringResource(id = R.string.enterage)) },
                singleLine = true,
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

            Spacer(modifier = Modifier.padding(30.dp))

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
                    viewModel.onEvent(AuthUiEvent.Register)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.createaccount),
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
                    viewModel.onEvent(AuthUiEvent.RegisterWBiometric)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.createaccountWB),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
    else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

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
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)){
                  Column(modifier = Modifier.weight(1f)){
                      OutlinedTextField(
                          value = state.registerUsername,
                          onValueChange = {
                              viewModel.onEvent(AuthUiEvent.RegisterUsernameChanged(it))
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
                          value = state.registerPassword,
                          onValueChange = {
                              viewModel.onEvent(AuthUiEvent.RegisterPasswordChanged(it))
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
                      OutlinedTextField(
                          value = state.registerFullName,
                          onValueChange = {
                              viewModel.onEvent(AuthUiEvent.RegisterFullNameChanged(it))
                          },
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(start = 16.dp, end = 16.dp),
                          label = { Text(stringResource(id = R.string.fullName), fontSize = 16.sp) },
                          placeholder = { Text(stringResource(id = R.string.enterfullName)) },
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


                  }
                  Column(modifier = Modifier.weight(1f)){
                      OutlinedTextField(
                          value = state.registerAge,
                          onValueChange = {
                              viewModel.onEvent(AuthUiEvent.RegisterAgeChanged(it))
                          },
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(start = 16.dp, end = 16.dp, bottom= 10.dp),
                          label = { Text(stringResource(id = R.string.age), fontSize = 16.sp) },
                          placeholder = { Text(stringResource(id = R.string.enterage)) },
                          singleLine = true,
                          shape = RoundedCornerShape(32.dp),
                          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                              viewModel.onEvent(AuthUiEvent.Register)
                          }
                      ) {
                          Text(
                              text = stringResource(id = R.string.createaccount),
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
                              viewModel.onEvent(AuthUiEvent.RegisterWBiometric)
                          }
                      ) {
                          Text(
                              text = stringResource(id = R.string.createaccountWB),
                              fontWeight = FontWeight.Bold,
                              fontSize = 15.sp
                          )
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