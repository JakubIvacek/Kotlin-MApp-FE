package stu.fiit.mtaa.fe.movenow.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.SubscriptionEventListener
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import kotlinx.coroutines.launch
import org.json.JSONObject
import stu.fiit.mtaa.fe.movenow.MainRepository
import stu.fiit.mtaa.fe.movenow.RetroService
import stu.fiit.mtaa.fe.movenow.data.AppUser
import stu.fiit.mtaa.fe.movenow.data.Friendship
import stu.fiit.mtaa.fe.movenow.models.Message
import stu.fiit.mtaa.fe.movenow.models.NewMessage

class ChatViewModel: ViewModel(){
    var state by mutableStateOf(ChatState())

    lateinit var friend: Friendship

    private val repository = MainRepository(RetroService.getInstance())


    fun fetchMessages(friendID: Long){
        state = state.copy(loading = true, messages = emptyList())

        viewModelScope.launch {
            state = try {
                val response = repository.getMessages(friendID)
                state.copy(
                    loading = false,
                    messages = response
                )
            }catch (e: Exception) {
                state.copy(
                    loading = false,
                    error = "Error fetching messages ${e.message}"
                )
            }
        }
    }
    fun sendMessage(message: NewMessage){
        viewModelScope.launch {
            repository.addMessage(friend.userId, message)
        }
        state = state.copy(messages = state.messages + Message(message.content, 0, friend.userId))
    }
    fun setUserID(it: Friendship) {
        friend = it
    }
    fun setUpPusher() {
        val options = PusherOptions()
        options.setCluster("eu")

        val pusher = Pusher("406496d9fc7f5e6862ef", options)

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {

            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
            }
        }, ConnectionState.ALL)

        val channel = pusher.subscribe(friend.friendshipId.toString())

        // Bind the event listener
        channel.bind("my-event", object : SubscriptionEventListener {
            override fun onEvent(channelName: String?, eventName: String?, data: String?) {
                // Handle the received data, for example:
                val message = data?.get(0)
                // Do something with the message
                fetchMessages(friend.userId)
            }
        })
    }
}


data class ChatState(
    val loading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val otherUser: List<AppUser> = emptyList(),
    val error: String = ""
)
