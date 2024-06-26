package stu.fiit.mtaa.fe.movenow.screens.addscreen.data

sealed class FriendActionResult {
    class Success : FriendActionResult()
    class NotFound : FriendActionResult()
    class UnknownError : FriendActionResult()
}