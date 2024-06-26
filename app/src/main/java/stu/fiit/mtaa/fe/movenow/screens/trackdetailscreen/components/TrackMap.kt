package stu.fiit.mtaa.fe.movenow.screens.trackdetailscreen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import stu.fiit.mtaa.fe.movenow.data.Activity

@Composable
fun TrackMap(
    modifier: Modifier,
    activity: Activity
){
    val startingPoint = activity.route[0]
    val endingPoint = activity.route[activity.route.lastIndex]
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startingPoint, 16f)
    }

    Card (
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ){
        GoogleMap(
            cameraPositionState=cameraPositionState
        ){
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
                points = activity.route,
                color = Color.Blue,
                width = 20f
            )
        }
    }
}