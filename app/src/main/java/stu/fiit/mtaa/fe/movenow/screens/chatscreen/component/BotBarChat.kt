package stu.fiit.mtaa.fe.movenow.screens.chatscreen.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import stu.fiit.mtaa.fe.movenow.ConnectionChecker
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.models.NewMessage
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.viewmodels.ChatViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel

@Composable
fun BotBarChat(navController: NavController, viewModel: MainViewModel, chatViewModel: ChatViewModel){
    var message by remember { mutableStateOf("") } // Make message mutable

    val context = LocalContext.current
    val windowInfo = rememberWindowInfo()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
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
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
                OutlinedTextField(
                    value = message,
                    onValueChange = { newValue ->
                        message = newValue // Update the message based on user input
                    },
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .width(300.dp),
                    label = { Text("New Message", fontSize = 16.sp) },
                    placeholder = { Text("Enter your message") },
                    singleLine = true,
                    shape = RoundedCornerShape(32.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        cursorColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }else{
                OutlinedTextField(
                    value = message,
                    onValueChange = { newValue ->
                        message = newValue // Update the message based on user input
                    },
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .width(500.dp),
                    label = { Text("New Message", fontSize = 16.sp) },
                    placeholder = { Text("Enter your message") },
                    singleLine = true,
                    shape = RoundedCornerShape(32.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        cursorColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }

            IconButton(
                modifier = Modifier
                    .padding(top = 8.dp),
                onClick = {
                    if (ConnectionChecker.isConnectionAvailable(context)) {
                        chatViewModel.sendMessage(NewMessage(message))
                        message = ""
                    }else{
                       Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                    }

                }
            ) {
                Icon(
                    modifier = Modifier.size(55.dp),
                    painter = painterResource(id = R.drawable.baseline_send_24),
                    contentDescription = "Send Message",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}