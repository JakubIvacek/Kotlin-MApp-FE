package stu.fiit.mtaa.fe.movenow.screens.trackingscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import stu.fiit.mtaa.fe.movenow.location.LocationData
import stu.fiit.mtaa.fe.movenow.screens.trackingscreen.components.Bar
import stu.fiit.mtaa.fe.movenow.screens.trackingscreen.components.TrackingControls
import stu.fiit.mtaa.fe.movenow.screens.trackingscreen.components.TrackingStats
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.TrackingState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackingScreen(
    viewModel: MainViewModel,
    location: LocationData,
    onNavigationBack: () -> Unit,
    onStopTracking: () -> Unit
){


    // User Position
    val userLocation = remember {
        mutableStateOf(LatLng(location.latitude,location.longitude))
    }

    // User position zoom
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(userLocation.value, 17f)
    }

    if (viewModel.trackingState.value == TrackingState.LAUNCHED){
        // Update the camera position when the location changes
        LaunchedEffect(viewModel.location.value) {
            viewModel.location.value?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), cameraPositionState.position.zoom)
            }
        }
    }


    Scaffold(
        topBar = { Bar(
            viewModel,
            onNavigationBack = onNavigationBack
        ) },
        floatingActionButton = {
            TrackingControls(
                viewModel = viewModel,
                onStopTracking = onStopTracking
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column (
            Modifier.padding(it)
        ){
            GoogleMap(
                modifier = Modifier
                    .weight(1f),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true)
            ){
                Polyline(
                    points = viewModel.locationUpdates.value,
                    color = Color.Red,
                    width = 10f
                )
            }
            TrackingStats(viewModel = viewModel)
        }
    }
}



