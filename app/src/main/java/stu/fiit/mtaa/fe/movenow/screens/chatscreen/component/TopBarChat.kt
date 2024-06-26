package stu.fiit.mtaa.fe.movenow.screens.chatscreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.data.AppUser
import stu.fiit.mtaa.fe.movenow.data.Friendship
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.viewmodels.ChatViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel



@Composable
fun TopBarChat(
    navController: NavController, viewModel: MainViewModel, appUser: Friendship,
    onBackClick: () -> Unit,
) {
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
        Row(modifier = Modifier.align(Alignment.Center)){
            IconButton(
                modifier = Modifier
                    .padding(end = 15.dp, top = 10.dp, start = 17.dp),
                onClick = onBackClick
            ) {
                Icon(
                    modifier = Modifier.size(55.dp),
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Delete friend",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            Image(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.no_avatar),
                contentDescription = "No avatar"
            )
            Column(
                Modifier.padding(16.dp,5.dp)
            ){
                Text(text = appUser.username,
                    style = TextStyle(fontSize = 25.sp),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = appUser.age + " " + appUser.fullName,
                    style = TextStyle(fontSize = 17.sp),
                    color = MaterialTheme.colorScheme.secondary
                )
            }

        }
    }
}