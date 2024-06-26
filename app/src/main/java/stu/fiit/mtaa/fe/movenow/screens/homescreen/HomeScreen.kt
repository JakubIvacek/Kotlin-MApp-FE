package stu.fiit.mtaa.fe.movenow.screens.homescreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import stu.fiit.mtaa.fe.movenow.BottomBar
import stu.fiit.mtaa.fe.movenow.ConnectionChecker
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.TopAppBar
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.data.Post
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.viewmodels.HomeViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.ProfileViewModel


@Composable
fun HomeScreen(
    userViewModel: ProfileViewModel,
    navController: NavController
    ){
    val viewModel: HomeViewModel = viewModel()
    val viewState by viewModel.homePageState
    val context = LocalContext.current
    val noConnection = R.string.noConnection
    val windowInfo = rememberWindowInfo()

    LaunchedEffect(key1 = Unit) {
        if (ConnectionChecker.isConnectionAvailable(context)){
            viewModel.fetchPosts()
            userViewModel.fetchCurrentUser()
        }else
            Toast.makeText(context, noConnection, Toast.LENGTH_LONG).show()
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
                BottomBar(navController = navController)
        },
        topBar = {
            TopAppBar(title = "Home", userFullName = userViewModel.state.currentUser?.fullName)
        }
    ) {

        when(viewState.loading){
            true -> {
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            false -> {
                if (viewState.list.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize()){
                        Text(text = stringResource(id = R.string.noPosts), modifier = Modifier.align(Alignment.Center))
                    }
                }else{
                    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
                        LazyColumn (
                            Modifier
                                .padding(it)
                                .background(MaterialTheme.colorScheme.surface)){
                            items(viewState.list){ post ->
                                HomeItem(post = post)
                            }
                        }
                    }else{
                        LazyRow (
                            Modifier
                                .padding(it)
                                .background(MaterialTheme.colorScheme.surface)){
                            items(viewState.list){ post ->
                                HomeItemExpanded(post = post)
                            }
                        }
                    }

                }

            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeItem(post: Post){
    Card (
        modifier = Modifier.padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = { /* TODO */}
    ) {
        Column {
            Map(post)
            HomeItemUser(post)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeItemExpanded(post: Post){
    Card (
        modifier = Modifier.padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = { /* TODO */}
    ) {
        Column (modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxHeight()){
            Map(post)
            HomeItemUser(post)
        }
    }
}



@Composable
fun HomeItemUser(post: Post){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = post.owner_username,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = "${post.type} : ${post.distance}KM  ${post.duration}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = "Detail"
            )
        }
    }
}


@Composable
fun Map(post: Post){

    val startingPoint = post.mapRoute[0]
    val endingPoint = post.mapRoute[post.mapRoute.lastIndex]
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startingPoint, 16f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(250.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        GoogleMap(cameraPositionState=cameraPositionState){
            Marker(
                state = MarkerState(position = startingPoint),
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
                title = "Start"
            )
            Marker(
                state = MarkerState(position = endingPoint),
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                title = "End"
            )

            Polyline(
                points = post.mapRoute,
                color = Color.Blue,
                width = 20f
            )
        }
    }

}


