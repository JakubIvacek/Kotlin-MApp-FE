package stu.fiit.mtaa.fe.movenow.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import stu.fiit.mtaa.fe.movenow.MainRepository
import stu.fiit.mtaa.fe.movenow.RetroService
import stu.fiit.mtaa.fe.movenow.data.Post
import stu.fiit.mtaa.fe.movenow.screens.trackingscreen.TrackingUtils

class HomeViewModel: ViewModel(){


    val homePageState = mutableStateOf(HomePageState())
    private val repository = MainRepository(RetroService.getInstance())

   fun fetchPosts() {

        homePageState.value = homePageState.value.copy(loading = true, list = emptyList())

        viewModelScope.launch {
            try {
                val response = repository.friendsActivities()
                homePageState.value = homePageState.value.copy(
                    loading = false,
                    list = response.map { activity ->
                        Post(
                            owner_username = activity.owner_username,
                            distance = activity.distance,
                            duration = TrackingUtils.getFormattedTime(activity.duration),
                            calories = activity.calories,
                            avgSpeed = activity.avgSpeed,
                            mapRoute = activity.mapRoute.split(". ")
                                .map { it.removePrefix("LatLng(").removeSuffix(")") }
                                .map { it.split(", ") }
                                .map { LatLng(it[0].toDouble(), it[1].toDouble()) },
                            type = activity.activityType,
                        )

                    }
                )
            } catch (e: Exception) {
                homePageState.value = homePageState.value.copy(
                    loading = false,
                    error = "Error fetching posts ${e.message}"
                )
            }
        }
    }


    data class HomePageState(
        val loading: Boolean = false,
        val list: List<Post> = emptyList(),
        val error: String? = null
    )

}