package stu.fiit.mtaa.fe.movenow.screens.profilescreens.utils

import stu.fiit.mtaa.fe.movenow.data.AppUser

data class ProfileScreenState(
    val loading: Boolean = false,
    val error: String = "",
    val updateFullName: String = "",
    val updateAge: String = "",
    val currentUser: AppUser? = null
)