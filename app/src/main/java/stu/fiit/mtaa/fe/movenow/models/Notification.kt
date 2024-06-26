package stu.fiit.mtaa.fe.movenow.models

import java.util.Date

data class Notification (
    val id: Long,
    val title: String,
    val body: String,
    val timestamp: Date,
    val user_id: Long
)