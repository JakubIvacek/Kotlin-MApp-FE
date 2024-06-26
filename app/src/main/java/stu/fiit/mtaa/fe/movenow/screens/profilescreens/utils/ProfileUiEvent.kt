package stu.fiit.mtaa.fe.movenow.screens.profilescreens.utils

sealed class ProfileUiEvent {
    data class FullNameChanged(val value: String): ProfileUiEvent()
    data class AgeChanged(val value: String): ProfileUiEvent()
    object Update: ProfileUiEvent()
}