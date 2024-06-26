package stu.fiit.mtaa.fe.movenow.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stu.fiit.mtaa.fe.movenow.MainRepository
import stu.fiit.mtaa.fe.movenow.RetroService
import stu.fiit.mtaa.fe.movenow.data.Activity
import stu.fiit.mtaa.fe.movenow.data.ActivityType
import stu.fiit.mtaa.fe.movenow.data.AddActivity
import stu.fiit.mtaa.fe.movenow.location.LocationData
import stu.fiit.mtaa.fe.movenow.screens.trackingscreen.TrackingUtils
import java.time.LocalDate

class MainViewModel: ViewModel() {

    private val repository = MainRepository(RetroService.getInstance())

    val state = mutableStateOf(HistoryScreenState())

    val pickedActivity = mutableStateOf<ActivityType>(ActivityType.Walking)
    val location = mutableStateOf<LocationData?>(null)
    val pickedDetailActivity = mutableStateOf<Activity?>(null)
    val locationUpdates = mutableStateOf<List<LatLng>>(listOf())
    val trackingState = mutableStateOf(TrackingState.INITIAL)
    val fromHistoryScreen = mutableStateOf(false)

    private var timeRunInMillis = mutableStateOf(0L)
    val timeRunInMillisText = mutableStateOf("00:00:00")
    private var lapTime = 0L
    private var timeStarted = 0L
    private var timeRun = 0L



    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchActivities() {
        state.value = state.value.copy(loading = true, list = emptyList())

        viewModelScope.launch {
            try {
                val response = repository.getAllActivities()
                state.value = state.value.copy(
                    loading = false,
                    list = response
                )
            } catch (e: Exception) {
                state.value = state.value.copy(
                    loading = false,
                    error = "Error fetching activities ${e.message}"
                )
            }
        }
    }


    fun launchTracking() {
        updateTrackingState(TrackingState.LAUNCHED)
        timeStarted = System.currentTimeMillis()
        CoroutineScope(Dispatchers.Main).launch {
            while (trackingState.value == TrackingState.LAUNCHED) {
                lapTime = System.currentTimeMillis() - timeStarted

                timeRunInMillis.value = timeRun + lapTime
                timeRunInMillisText.value = TrackingUtils.getFormattedTime(timeRunInMillis.value)
                delay(50L)
            }
            timeRun += lapTime
        }
    }
    fun pauseTracking() = updateTrackingState(TrackingState.PAUSED)

    @RequiresApi(Build.VERSION_CODES.O)
    fun stopTracking() {
        updateTrackingState(TrackingState.STOPPED)
        val activity = Activity(LocalDate.now(), pickedActivity.value, TrackingUtils.getFormattedTime(timeRunInMillis.value), "25,5", "3.7", "320", locationUpdates.value)
        addActivity(activity, timeRunInMillis.value)
        fromHistoryScreen.value = false
        pickedDetailActivity.value = activity
    }

    fun resetTracking() {
        timeRunInMillis.value = 0L
        timeRunInMillisText.value = "00:00:00"
        lapTime = 0L
        timeStarted = 0L
        timeRun = 0L
        locationUpdates.value = listOf()
        updateTrackingState(TrackingState.INITIAL)
    }

    private fun addActivity(activity: Activity, duration: Long) {
        val request = AddActivity(
            date = activity.date.toString(),
            distance = activity.distance,
            avgSpeed = activity.avgSpeed,
            calories = activity.calories,
            duration = duration,
            activityType = activity.type.name,
            mapRoute = activity.route.joinToString(separator = ". ") {
                "LatLng(${it.latitude}, ${it.longitude})"
            }
        )

        viewModelScope.launch {
            repository.addActivity(request)
        }
    }

    fun updateLocation(newLocation: LocationData) {
        location.value = newLocation
        if (trackingState.value == TrackingState.LAUNCHED) locationUpdates.value += LatLng(newLocation.latitude, newLocation.longitude)
    }

    fun pickActivity(activityType: ActivityType) {
        locationUpdates.value = listOf()
        timeRunInMillis.value = 0L
        updateTrackingState(TrackingState.INITIAL)
        pickedActivity.value = activityType
    }

    fun pickDetailActivity(activity: Activity) {
        fromHistoryScreen.value = true
        pickedDetailActivity.value = activity
    }

    private fun updateTrackingState(state: TrackingState) {
        trackingState.value = state
    }


    data class HistoryScreenState(
        val loading: Boolean = false,
        val list: List<Activity> = emptyList(),
        val error: String? = null
    )

}

enum class TrackingState {
    INITIAL,
    LAUNCHED,
    PAUSED,
    STOPPED
}