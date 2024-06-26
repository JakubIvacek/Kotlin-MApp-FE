package stu.fiit.mtaa.fe.movenow.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import stu.fiit.mtaa.fe.movenow.MainRepository
import stu.fiit.mtaa.fe.movenow.RetroService
import stu.fiit.mtaa.fe.movenow.data.AppUser
import stu.fiit.mtaa.fe.movenow.data.Friendship
import stu.fiit.mtaa.fe.movenow.screens.addscreen.data.FriendActionResult

class FriendsViewModel: ViewModel(){

    var state by mutableStateOf(FriendsState())

    private val repository = MainRepository(RetroService.getInstance())

    private val resultChannel = Channel<FriendActionResult>()
    val friendActionResult = resultChannel.receiveAsFlow()

    fun fetchFriends() {
        state = state.copy(loading = true, friendsList = emptyList())

        viewModelScope.launch {
            state = try {
                val response = repository.getFriends()
                state.copy(
                    loading = false,
                    friendsList = response
                )
            } catch (e: Exception) {
                state.copy(
                    loading = false,
                    error = "Error fetching friends ${e.message}"
                )
            }
        }
    }

    fun fetchNotFriends() {
        state = state.copy(loading = true, addFriendsList = emptyList())

        viewModelScope.launch {
            state = try {
                val response = repository.getAddFriends()
                state.copy(
                    loading = false,
                    addFriendsList = response
                )
            } catch (e: Exception) {
                state.copy(
                    loading = false,
                    error = "Error fetching add friends ${e.message}"
                )
            }
        }
    }

    fun addFriend(friendUsername: String) {
        viewModelScope.launch {
            val result = repository.addFriend(friendUsername)

            if (result is FriendActionResult.Success) {
                state = state.copy(
                    addFriendsList = state.addFriendsList.filter { it.username != friendUsername }
                )
            }
            resultChannel.send(result)
        }
    }

    fun deleteFriend(friendshipId: Long) {
        viewModelScope.launch {
            val result = repository.deleteFriendship(friendshipId)

            if (result is FriendActionResult.Success) {
                state = state.copy(
                    friendsList = state.friendsList.filter { it.friendshipId != friendshipId }
                )
            }
            resultChannel.send(result)
        }
    }

}



data class FriendsState(
    val loading: Boolean = false,
    val friendsList: List<Friendship> = emptyList(),
    val addFriendsList: List<AppUser> = emptyList(),
    val error: String = ""
)