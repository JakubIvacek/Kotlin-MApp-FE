package stu.fiit.mtaa.fe.movenow.auth

data class AuthState(
    val isLoading: Boolean = false,
    val registerUsername: String = "",
    val registerPassword: String = "",
    val registerFullName: String = "",
    val registerAge: String = "",
    val signInUsername: String = "",
    val signInPassword: String = "",
    val biometricLogIn: Boolean = false
)
