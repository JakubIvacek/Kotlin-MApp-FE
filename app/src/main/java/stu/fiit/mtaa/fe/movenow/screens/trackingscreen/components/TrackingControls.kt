package stu.fiit.mtaa.fe.movenow.screens.trackingscreen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.TrackingState


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackingControls(
    viewModel: MainViewModel,
    onStopTracking: () -> Unit
){
    Row {
        when (viewModel.trackingState.value) {
            TrackingState.INITIAL -> {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(80.dp),
                    backgroundColor = Color.Blue,
                    onClick = { viewModel.launchTracking() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.start_tracking_50),
                        tint = Color.White,
                        contentDescription = "Start Tracking"
                    )
                }
            }
            TrackingState.LAUNCHED -> {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(80.dp),
                    backgroundColor = Color.LightGray,
                    onClick = { viewModel.pauseTracking() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pause_tracking_24),
                        tint = Color.White,
                        contentDescription = "Pause Tracking"
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(80.dp),
                    backgroundColor = Color.Red,
                    onClick = {
                        viewModel.stopTracking()
                        onStopTracking()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.stop_tracking_24),
                        tint = Color.White,
                        contentDescription = "Stop Tracking"
                    )
                }
            }

            TrackingState.PAUSED -> {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(80.dp),
                    backgroundColor = Color.Blue,
                    onClick = { viewModel.launchTracking() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.start_tracking_50),
                        tint = Color.White,
                        contentDescription = "Resume Tracking"
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(80.dp),
                    backgroundColor = Color.Red,
                    onClick = {
                        viewModel.stopTracking()
                        onStopTracking()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.stop_tracking_24),
                        tint = Color.White,
                        contentDescription = "Stop Tracking"
                    )
                }
            }
            TrackingState.STOPPED -> {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(80.dp),
                    backgroundColor = Color.Blue,
                    onClick = { viewModel.launchTracking() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.start_tracking_50),
                        tint = Color.White,
                        contentDescription = "Start Tracking"
                    )
                }
            }
        }
    }
}