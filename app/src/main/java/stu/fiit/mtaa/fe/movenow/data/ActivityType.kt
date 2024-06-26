package stu.fiit.mtaa.fe.movenow.data

import androidx.annotation.DrawableRes
import stu.fiit.mtaa.fe.movenow.R

sealed class ActivityType(
    val name: String,
    @DrawableRes val icon: Int
) {
    data object Hiking: ActivityType(
        "Hiking",
        R.drawable.baseline_hiking_24
    )

    data object Cycling: ActivityType(
        "Cycling",
        R.drawable.baseline_pedal_bike_24
    )

    data object Walking: ActivityType(
        "Walking",
        R.drawable.baseline_directions_walk_24
    )

    data object Running: ActivityType(
        "Running",
        R.drawable.baseline_directions_run_24
    )
}

val activityTypes = listOf(
    ActivityType.Walking,
    ActivityType.Running,
    ActivityType.Cycling,
    ActivityType.Hiking
)