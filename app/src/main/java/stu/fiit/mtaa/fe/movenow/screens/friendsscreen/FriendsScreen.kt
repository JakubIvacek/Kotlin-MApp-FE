package stu.fiit.mtaa.fe.movenow.screens.friendsscreen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import stu.fiit.mtaa.fe.movenow.BottomBar
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.data.Friendship
import stu.fiit.mtaa.fe.movenow.data.User
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.screens.addscreen.data.FriendActionResult
import stu.fiit.mtaa.fe.movenow.screens.friendsscreen.components.FriendItem
import stu.fiit.mtaa.fe.movenow.screens.friendsscreen.components.FriendItemExpand
import stu.fiit.mtaa.fe.movenow.viewmodels.FriendsViewModel


@Composable
fun FriendsScreen(
    navController: NavController,
    friendsViewModel: FriendsViewModel,
    onChatClick: (Friendship) -> Unit,
    onAddClick: () -> Unit,
){
    val windowInfo = rememberWindowInfo()
    val context = LocalContext.current
    val friendRemoved = stringResource(id = R.string.friendRemoved)

    LaunchedEffect(key1 = Unit) {
        friendsViewModel.fetchFriends()
    }

    LaunchedEffect(key1 = friendsViewModel, key2 = context) {
        friendsViewModel.friendActionResult.collect { result ->
            when(result) {
                is FriendActionResult.Success -> {
                    Toast.makeText(context, friendRemoved,  Toast.LENGTH_SHORT).show()
                }
                is FriendActionResult.NotFound -> {
                    Toast.makeText(context, "Friendship don't found",  Toast.LENGTH_SHORT).show()
                }
                is FriendActionResult.UnknownError -> {
                    Toast.makeText(context, "Unknown error",  Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController = navController)
        },
        topBar = {
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
                Row(modifier = Modifier.padding(bottom = 15.dp)) {
                    Text(
                        text = stringResource(id = R.string.friends),
                        style = TextStyle(fontSize = 31.sp, fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 140.dp, top = 20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier
                            .padding(end = 22.dp, top = 13.dp),
                        onClick = onAddClick
                    ) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            painter = painterResource(id = R.drawable.baseline_person_add_24),
                            contentDescription = "Add Friend",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    ) {
        if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
            LazyColumn(
                Modifier
                    .fillMaxHeight()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.surface)
            ) {

                items(friendsViewModel.state.friendsList) { user ->
                    FriendItem(
                        appUser = user,
                        onChatClick = {onChatClick(user)},
                        onDeleteClick = { friendsViewModel.deleteFriend(user.friendshipId) }
                    )
                }
            }
        }else{
            LazyRow(
                Modifier
                    .fillMaxHeight()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.surface)
            ) {

                items(friendsViewModel.state.friendsList) { user ->
                    FriendItemExpand(
                        appUser = user,
                        onChatClick = {onChatClick(user)},
                        onDeleteClick = { friendsViewModel.deleteFriend(user.friendshipId) }
                    )
                }
            }
        }
    }
}


