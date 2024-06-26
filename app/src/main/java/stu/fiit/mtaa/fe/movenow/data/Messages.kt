package stu.fiit.mtaa.fe.movenow.data

import java.util.Date

data class Message(
    val message: String,
    val timeOf: String,
    val yours: Boolean
)

data class MessagesResponse(val messages: List<Message>)

val messages = listOf(
    Message(
        "Ahoj",
        "2024-12-02 12:44:35",
        true
    ),
    Message(
        "Ahoj kamo",
        "2024-12-02 12:44:55",
        false
    ),
    Message(
        "Co jak to ide",
        "2024-12-02 12:45:55",
        false
    ),
    Message(
        "Dobro dobroDobro dobroDobro dobroDobro dobroDobro dobroDobro dobroDobro dobro ",
        "2024-12-02 12:47:55",
        true
    ),
    Message(
        "a tebe co jak to ide ?",
        "2024-12-02 12:48:05",
        true
    ),
    Message(
        "Pomalicky vsak vis raz tak raz onak a tak onak a to a onak a noank",
        "2024-12-02 12:50:05",
        false
    ),
)