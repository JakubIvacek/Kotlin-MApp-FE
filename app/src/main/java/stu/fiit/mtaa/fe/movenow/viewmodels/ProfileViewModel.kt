package stu.fiit.mtaa.fe.movenow.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stu.fiit.mtaa.fe.movenow.MainRepository
import stu.fiit.mtaa.fe.movenow.RetroService
import stu.fiit.mtaa.fe.movenow.screens.profilescreens.utils.ProfileScreenState
import stu.fiit.mtaa.fe.movenow.screens.profilescreens.utils.ProfileUiEvent

class ProfileViewModel: ViewModel() {

    var state by mutableStateOf(ProfileScreenState())

    private val repository = MainRepository(RetroService.getInstance())


    fun onEvent(event: ProfileUiEvent) {
        when(event) {
            is ProfileUiEvent.AgeChanged -> {
                state = state.copy(updateAge = event.value)
            }
            is ProfileUiEvent.FullNameChanged -> {
                state = state.copy(updateFullName = event.value)
            }
            ProfileUiEvent.Update -> {
                update()
            }
        }
    }

    private fun update() {
        state = state.copy(loading = true)

        val currentUser = state.currentUser ?: return

        viewModelScope.launch {
            state = try {
                val response = repository.updateCurrentUser(
                    if (state.updateFullName == "") currentUser.fullName
                            else state.updateFullName
                    ,
                    if (state.updateAge == "") currentUser.age
                            else state.updateAge
                )
                state.copy(
                    loading = false,
                    currentUser = response
                )
            } catch (e: Exception) {
                state.copy(
                    loading = false,
                    error = "Error updating user ${e.message}"
                )
            }
        }
    }

    fun fetchCurrentUser() {
        state = state.copy(loading = true, currentUser = null)

        viewModelScope.launch {
            state = try {
                val response = repository.getCurrentUser()
                state.copy(
                    loading = false,
                    currentUser = response
                )
            } catch (e: Exception) {
                state.copy(
                    loading = false,
                    error = "Error fetching current user ${e.message}"
                )
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            repository.logOut()
        }
    }
}