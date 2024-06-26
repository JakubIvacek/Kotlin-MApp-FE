package stu.fiit.mtaa.fe.movenow.models

data class Activity(
    val id: Long,
    val user_id: Long,
    val calories: Int,
    val activityType: String,
    val distance: Float,
    val duration: Float,
    val shared: Boolean,
    val map_route: List<Float>
)