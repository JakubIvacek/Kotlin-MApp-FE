package stu.fiit.mtaa.fe.movenow.data

import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate


data class Activity(
    val date: LocalDate,
    val type: ActivityType,
    val duration: String,
    val avgSpeed: String,
    val distance: String,
    val calories: String,
    val route: List<LatLng>
)

data class ActivityResponse(
    val id: Long,
    val distance: String,
    val duration: Long,
    val avgSpeed: String,
    val calories: String,
    val mapRoute: String,
    val activityType: String,
    val date: String,
)
