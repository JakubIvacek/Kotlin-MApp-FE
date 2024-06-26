package stu.fiit.mtaa.fe.movenow.models


data class Message(
    val content: String,
    val sender_id: Long,
    val recipient_id: Long
)