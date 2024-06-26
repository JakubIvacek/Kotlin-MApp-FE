package stu.fiit.mtaa.fe.movenow.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import stu.fiit.mtaa.fe.movenow.MainRepository
import stu.fiit.mtaa.fe.movenow.Prefs
import stu.fiit.mtaa.fe.movenow.RetroService
import stu.fiit.mtaa.fe.movenow.auth.AuthResult
import stu.fiit.mtaa.fe.movenow.auth.AuthState
import stu.fiit.mtaa.fe.movenow.auth.AuthUiEvent

class AuthViewModel: ViewModel() {

    private val repository = MainRepository(RetroService.getInstance())

    var state by mutableStateOf(AuthState())

    private val prefs = Prefs.getInstance()

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun onEvent(event: AuthUiEvent) {
        when(event) {
            AuthUiEvent.Register -> {
                register()
            }
            is AuthUiEvent.RegisterAgeChanged -> {
                state = state.copy(registerAge = event.value)
            }
            is AuthUiEvent.RegisterFullNameChanged -> {
                state = state.copy(registerFullName = event.value)
            }
            is AuthUiEvent.RegisterPasswordChanged -> {
                state = state.copy(registerPassword = event.value)
            }
            is AuthUiEvent.RegisterUsernameChanged -> {
                state = state.copy(registerUsername = event.value)
            }
            AuthUiEvent.SignIn -> {
                logIn()
            }
            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }
            is AuthUiEvent.SignInUsernameChanged -> {
                state = state.copy(signInUsername = event.value)
            }
            is AuthUiEvent.RegisterWBiometric-> {
                prefs.edit().putString("username", state.registerUsername).apply()
                prefs.edit().putString("password", state.registerPassword).apply()
                register()
            }
            is AuthUiEvent.SignInWBiometric -> {
                if (state.biometricLogIn) {
                    val username = prefs.getString("username", null)
                    val password = prefs.getString("password", null)
                    if (username != null && password != null) {
                        state = state.copy(signInUsername = username)
                        state = state.copy(signInPassword = password)
                        state = state.copy(biometricLogIn = false)
                        logIn()
                    }
                }
            }
        }
    }

    fun onBiometricClick() {
        state = state.copy(biometricLogIn = true)
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.register(
                username = state.registerUsername,
                password = state.registerPassword,
                fullName = state.registerFullName,
                age = state.registerAge
            )
            if (result is AuthResult.Authorized) {
                //sendFcmToken()
            }
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun logIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.logIn(
                username = state.signInUsername,
                password = state.signInPassword
            )
            if (result is AuthResult.Authorized) {
                //sendFcmToken()
            }
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun sendFcmToken(){
        viewModelScope.launch {
            val token = Firebase.messaging.token.await()
            repository.sendFcmToken(token)
        }
    }
}

enum class ErrorState {
    EMPTY_FIELD,
    INVALID_CREDENTIALS,
    BIOMETRIC_OFF
}