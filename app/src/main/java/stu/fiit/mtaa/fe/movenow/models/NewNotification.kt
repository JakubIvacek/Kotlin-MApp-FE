package stu.fiit.mtaa.fe.movenow.models

data class NewNotification(
    val notificationTypeEnum: String,
    val title: String,
    val body: String,
    val user_id: Long
)