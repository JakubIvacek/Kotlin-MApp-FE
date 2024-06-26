package stu.fiit.mtaa.fe.movenow.data


data class FriendsActivity(
    val id: Long,
    val owner_username: String,
    val distance: String,
    val duration: Long,
    val calories: String,
    val avgSpeed: String,
    val mapRoute: String,
    val activityType: String
    )