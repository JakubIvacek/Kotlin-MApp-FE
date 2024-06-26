package stu.fiit.mtaa.fe.movenow.screens.chatscreen.data

import stu.fiit.mtaa.fe.movenow.screens.addscreen.data.FriendActionResult

sealed class ChatActionResult {
    class Success : ChatActionResult()
    class NotFound : ChatActionResult()
    class UnknownError : ChatActionResult()
}