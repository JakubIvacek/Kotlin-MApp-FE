package stu.fiit.mtaa.fe.movenow.auth

sealed class AuthUiEvent {
    data class RegisterUsernameChanged(val value: String): AuthUiEvent()
    data class RegisterPasswordChanged(val value: String): AuthUiEvent()
    data class RegisterFullNameChanged(val value: String): AuthUiEvent()
    data class RegisterAgeChanged(val value: String): AuthUiEvent()
    object Register: AuthUiEvent()
    object RegisterWBiometric: AuthUiEvent()

    data class SignInUsernameChanged(val value: String): AuthUiEvent()
    data class SignInPasswordChanged(val value: String): AuthUiEvent()
    object SignIn: AuthUiEvent()
    object SignInWBiometric: AuthUiEvent()
}