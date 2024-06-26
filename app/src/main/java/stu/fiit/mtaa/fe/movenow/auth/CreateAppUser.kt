package stu.fiit.mtaa.fe.movenow.auth

data class CreateAppUser(
    val username: String,
    val password: String,
    val fullName: String,
    val age: String,
)