package stu.fiit.mtaa.fe.movenow.screens

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import stu.fiit.mtaa.fe.movenow.BottomBar
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.TopAppBar
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel
import androidx.navigation.NavController
import stu.fiit.mtaa.fe.movenow.data.ActivityType
import stu.fiit.mtaa.fe.movenow.MainActivity
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.location.LocationUtils
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo

@Composable
fun PickActivityScreen(
    navController: NavController,
    viewModel: MainViewModel,
    locationUtils: LocationUtils,
    context: Context,
    onActivityClick: (ActivityType) -> Unit
) {
    val windowInfo = rememberWindowInfo()
    LaunchedEffect(key1 = Unit) {
        viewModel.resetTracking()
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
                permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true){
                locationUtils.requestLocationUpdates(viewModel)
            }else{
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (rationaleRequired){
                    Toast.makeText(context,
                        "Location Permission is required for this feature to work", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,
                        "Location Permission is required Please enable it in Android Settings", Toast.LENGTH_LONG).show()
                }
            }
        }
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController = navController)
        },
        topBar = {
            TopAppBar(title = "Track")
        }
    ) {
        if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
            Column(
                Modifier.padding(it)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(5.dp)
                ){
                    Text(
                        text = stringResource(id = R.string.pickActivity),
                        fontSize = 20.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(32.dp))
                        .padding(16.dp)
                        .clickable {
                            // Zistujeme ci mame location permission
                            if (locationUtils.hasLocationPermission(context)) {
                                locationUtils.requestLocationUpdates(viewModel) // Ak ano tak môžeme pristupovat k polohe
                                onActivityClick(ActivityType.Hiking)
                            } else {
                                requestPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_hiking_24),
                            contentDescription = "Hiking",
                            tint = Color.Black,
                            modifier = Modifier.size(55.dp)
                        )

                        Spacer(modifier = Modifier.width(50.dp))

                        Text(
                            text = stringResource(id = R.string.hiking),
                            fontSize = 30.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(55.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                            contentDescription = "Tracking",
                            tint = Color.Black,
                            modifier = Modifier.size(55.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(32.dp))
                        .padding(16.dp)
                        .clickable {
                            // Zistujeme ci mame location permission
                            if (locationUtils.hasLocationPermission(context)) {
                                locationUtils.requestLocationUpdates(viewModel) // Ak ano tak môžeme pristupovat k polohe
                                onActivityClick(ActivityType.Cycling)
                            } else {
                                requestPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_pedal_bike_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(55.dp)
                        )

                        Spacer(modifier = Modifier.width(45.dp))

                        Text(
                            text = stringResource(id = R.string.cycling),
                            fontSize = 30.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(50.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(55.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(32.dp))
                        .padding(16.dp)
                        .clickable {
                            // Zistujeme ci mame location permission
                            if (locationUtils.hasLocationPermission(context)) {
                                locationUtils.requestLocationUpdates(viewModel) // Ak ano tak môžeme pristupovat k polohe
                                onActivityClick(ActivityType.Walking)
                            } else {
                                requestPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_directions_walk_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(55.dp)
                        )

                        Spacer(modifier = Modifier.width(40.dp))

                        Text(
                            text = stringResource(id = R.string.walking),
                            fontSize = 30.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(45.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(55.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(32.dp))
                        .padding(16.dp)
                        .clickable {
                            // Zistujeme ci mame location permission
                            if (locationUtils.hasLocationPermission(context)) {
                                locationUtils.requestLocationUpdates(viewModel) // Ak ano tak môžeme pristupovat k polohe
                                onActivityClick(ActivityType.Running)
                            } else {
                                requestPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_directions_run_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(55.dp)
                        )

                        Spacer(modifier = Modifier.width(40.dp))

                        Text(
                            text = stringResource(id = R.string.running),
                            fontSize = 30.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(40.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(55.dp)
                        )
                    }
                }
            }
        }else{
            Column(){
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(5.dp)
                ){
                    Text(
                        text = stringResource(id = R.string.pickActivity),
                        fontSize = 20.sp
                    )
                }
                Row(
                    Modifier.padding(it)
                ) {
                    Column(modifier = Modifier.weight(1f)){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.LightGray, shape = RoundedCornerShape(32.dp))
                                .padding(16.dp)
                                .clickable {
                                    // Zistujeme ci mame location permission
                                    if (locationUtils.hasLocationPermission(context)) {
                                        locationUtils.requestLocationUpdates(viewModel) // Ak ano tak môžeme pristupovat k polohe
                                        onActivityClick(ActivityType.Hiking)
                                    } else {
                                        requestPermissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            )
                                        )
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_hiking_24),
                                    contentDescription = "Hiking",
                                    tint = Color.Black,
                                    modifier = Modifier.size(55.dp)
                                )

                                Spacer(modifier = Modifier.width(50.dp))

                                Text(
                                    text = stringResource(id = R.string.hiking),
                                    fontSize = 30.sp,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.width(55.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                                    contentDescription = "Tracking",
                                    tint = Color.Black,
                                    modifier = Modifier.size(55.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.LightGray, shape = RoundedCornerShape(32.dp))
                                .padding(16.dp)
                                .clickable {
                                    // Zistujeme ci mame location permission
                                    if (locationUtils.hasLocationPermission(context)) {
                                        locationUtils.requestLocationUpdates(viewModel) // Ak ano tak môžeme pristupovat k polohe
                                        onActivityClick(ActivityType.Cycling)
                                    } else {
                                        requestPermissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            )
                                        )
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_pedal_bike_24),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(55.dp)
                                )

                                Spacer(modifier = Modifier.width(45.dp))

                                Text(
                                    text = stringResource(id = R.string.cycling),
                                    fontSize = 30.sp,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.width(50.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(55.dp)
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.LightGray, shape = RoundedCornerShape(32.dp))
                                .padding(16.dp)
                                .clickable {
                                    // Zistujeme ci mame location permission
                                    if (locationUtils.hasLocationPermission(context)) {
                                        locationUtils.requestLocationUpdates(viewModel) // Ak ano tak môžeme pristupovat k polohe
                                        onActivityClick(ActivityType.Walking)
                                    } else {
                                        requestPermissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            )
                                        )
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_directions_walk_24),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(55.dp)
                                )

                                Spacer(modifier = Modifier.width(40.dp))

                                Text(
                                    text = stringResource(id = R.string.walking),
                                    fontSize = 30.sp,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.width(45.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(55.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(color = Color.LightGray, shape = RoundedCornerShape(32.dp))
                                .padding(16.dp)
                                .clickable {
                                    // Zistujeme ci mame location permission
                                    if (locationUtils.hasLocationPermission(context)) {
                                        locationUtils.requestLocationUpdates(viewModel) // Ak ano tak môžeme pristupovat k polohe
                                        onActivityClick(ActivityType.Walking)
                                    } else {
                                        requestPermissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            )
                                        )
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_directions_run_24),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(55.dp)
                                )

                                Spacer(modifier = Modifier.width(40.dp))

                                Text(
                                    text = stringResource(id = R.string.running),
                                    fontSize = 30.sp,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.width(45.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(55.dp)
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}