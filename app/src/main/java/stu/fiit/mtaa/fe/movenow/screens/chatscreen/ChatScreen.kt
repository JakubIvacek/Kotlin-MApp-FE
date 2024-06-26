package stu.fiit.mtaa.fe.movenow.screens.chatscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.screens.chatscreen.component.BotBarChat
import stu.fiit.mtaa.fe.movenow.screens.chatscreen.component.TopBarChat
import stu.fiit.mtaa.fe.movenow.viewmodels.ChatViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel


@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel,
    viewModel: MainViewModel,
    onBackClick: () -> Unit
){

    // State variable to trigger recomposition
    var refreshScreen by remember { mutableStateOf(false) }
    val windowInfo = rememberWindowInfo()

    LaunchedEffect(key1 = Unit) {
        chatViewModel.setUpPusher()
        chatViewModel.fetchMessages(chatViewModel.friend.userId)
    }

    // Observe events and trigger refresh when needed
    LaunchedEffect(chatViewModel.state.messages) {
        refreshScreen = !refreshScreen
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BotBarChat(navController = navController, viewModel = viewModel, chatViewModel)
        },
        topBar = {
            TopBarChat(navController, viewModel, chatViewModel.friend, onBackClick)
        }
    ) {
        LazyColumn (
            Modifier
                .padding(it)
                .background(color = MaterialTheme.colorScheme.background)){
            val count = 0
            var color = Color.Blue
            var colorText = Color.White
            val otherUserID = chatViewModel.friend.userId
            items(chatViewModel.state.messages){ message ->
                if( message.sender_id != otherUserID) {
                    color = Color.Blue;
                    colorText = Color.White
                }
                else  {
                    color = Color.LightGray;
                    colorText = Color.Black
                }
                Row(Modifier.padding(start=10.dp, end= 10.dp, top = 10.dp)
                ){
                    if(message.sender_id != otherUserID) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .then(Modifier.widthIn(min = 100.dp, max = 300.dp))
                            .then(Modifier.heightIn(min = 70.dp))
                            .clip(RoundedCornerShape(24.dp))
                            .drawWithContent {
                                drawContent()

                                val shadowColor = Color(0, 0, 0, alpha = 40)
                                val shadowHeight = 2.dp

                                drawRect(
                                    color = shadowColor,
                                    topLeft = Offset(0f, size.height - shadowHeight.toPx()),
                                    size = Size(size.width, shadowHeight.toPx())
                                )
                            }
                            .background(color = color)
                            .padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
                    ) {
                        Column(
                            Modifier.padding(16.dp, 5.dp)
                        ) {
                            Text(
                                text = message.content,
                                style = TextStyle(fontSize = 18.sp),
                                color = colorText,
                            )
                        }
                    }
                    if(message.sender_id == otherUserID) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}


