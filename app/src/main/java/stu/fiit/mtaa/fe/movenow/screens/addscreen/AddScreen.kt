package stu.fiit.mtaa.fe.movenow.screens.addscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stu.fiit.mtaa.fe.movenow.ConnectionChecker
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.screens.addscreen.component.TopBarAdd
import stu.fiit.mtaa.fe.movenow.screens.addscreen.data.FriendActionResult
import stu.fiit.mtaa.fe.movenow.viewmodels.FriendsViewModel

@Composable
fun AddScreen(
    friendsViewModel: FriendsViewModel,
    onBackClick: () -> Unit
){
    val context = LocalContext.current
    val friendAdded = stringResource(id = R.string.friendAdd)
    val noInternet = stringResource(id = R.string.noConnection)
    val windowInfo = rememberWindowInfo()

    LaunchedEffect(key1 = Unit) {
        if (ConnectionChecker.isConnectionAvailable(context)) {
            friendsViewModel.fetchNotFriends()
        } else {
            Toast.makeText(context, noInternet, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = friendsViewModel, key2 = context) {
        friendsViewModel.friendActionResult.collect { result ->
            when(result) {
                is FriendActionResult.Success -> {
                    Toast.makeText(context, friendAdded,  Toast.LENGTH_SHORT).show()
                }
                is FriendActionResult.NotFound -> {
                    Toast.makeText(context, "User not found",  Toast.LENGTH_SHORT).show()
                }
                is FriendActionResult.UnknownError -> {
                    Toast.makeText(context, "Unknown error",  Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBarAdd(onBackClick) }
    ) {

        when(friendsViewModel.state.loading){
            true -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
            false -> {
                if (friendsViewModel.state.addFriendsList.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize()){
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.noFriends),
                            style = TextStyle(fontSize = 20.sp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }else{
                    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
                        LazyColumn (
                            Modifier
                                .padding(it)
                                .background(MaterialTheme.colorScheme.surface)){
                            items(friendsViewModel.state.addFriendsList){ user ->
                                Column (
                                    modifier = Modifier.padding(vertical = 5.dp)
                                        .padding(end = 10.dp, start=10.dp)
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 5.dp, end = 10.dp, start=10.dp),
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
                                                Text(text = user.username,
                                                    style = TextStyle(fontSize = 18.sp),
                                                    color = MaterialTheme.colorScheme.secondary
                                                )
                                                Text(
                                                    text = user.fullName + " " + user.age,
                                                    style = TextStyle(fontSize = 14.sp),
                                                    color = MaterialTheme.colorScheme.secondary
                                                )

                                            }
                                            Spacer(modifier = Modifier.weight(1f))
                                            IconButton(
                                                modifier = Modifier
                                                    .padding(end = 10.dp),
                                                onClick = {
                                                    if (ConnectionChecker.isConnectionAvailable(context)){
                                                        friendsViewModel.addFriend(user.username)
                                                    } else {
                                                        Toast.makeText(context, noInternet, Toast.LENGTH_SHORT).show()
                                                    }
                                                }
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
                            }
                        }
                    }else{
                        LazyRow (
                            Modifier
                                .padding(it)
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)){
                            items(friendsViewModel.state.addFriendsList){ user ->
                                Row (
                                    modifier = Modifier.padding(vertical = 5.dp)
                                        .fillMaxHeight()
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                            .padding(top = 5.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
                                        elevation = CardDefaults.cardElevation(5.dp),
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Spacer(modifier = Modifier.weight(1f))
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
                                                Text(text = user.username,
                                                    style = TextStyle(fontSize = 18.sp),
                                                    color = MaterialTheme.colorScheme.secondary
                                                )
                                                Text(
                                                    text = user.fullName + " " + user.age,
                                                    style = TextStyle(fontSize = 14.sp),
                                                    color = MaterialTheme.colorScheme.secondary
                                                )

                                            }
                                            Spacer(modifier = Modifier.weight(1f))
                                            IconButton(
                                                modifier = Modifier
                                                    .padding(end = 10.dp),
                                                onClick = {
                                                    if (ConnectionChecker.isConnectionAvailable(context)){
                                                        friendsViewModel.addFriend(user.username)
                                                    } else {
                                                        Toast.makeText(context, noInternet, Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    modifier = Modifier.size(50.dp),
                                                    painter = painterResource(id = R.drawable.baseline_person_add_24),
                                                    contentDescription = "Add Friend",
                                                    tint = MaterialTheme.colorScheme.tertiary
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}