package stu.fiit.mtaa.fe.movenow.screens.friendsscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.data.Friendship

@Composable
fun FriendItem(
    appUser: Friendship,
    onChatClick: () -> Unit,
    onDeleteClick: () -> Unit
    ){
    Column (
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            ){
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.no_avatar),
                    contentDescription = "No avatar"
                )
                Column(
                    Modifier.padding(16.dp,5.dp)
                ){
                    Text(text = appUser.username,
                        style = TextStyle(fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = appUser.fullName + "  " + appUser.age,
                        style = TextStyle(fontSize = 14.sp),
                        color = MaterialTheme.colorScheme.secondary
                    )

                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    onClick = onChatClick
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.baseline_chat_24),
                        contentDescription = "Chat friend",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(end = 10.dp),
                    onClick = onDeleteClick
                ) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                        contentDescription = "Delete friend",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
