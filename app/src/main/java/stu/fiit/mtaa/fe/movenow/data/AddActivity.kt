package stu.fiit.mtaa.fe.movenow.data

data class AddActivity (
    val calories: String,
    val activityType: String,
    val distance: String,
    val duration: Long,
    val avgSpeed: String,
    val date: String,
    val mapRoute: String
)