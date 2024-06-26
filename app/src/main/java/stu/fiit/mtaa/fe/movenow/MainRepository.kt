package stu.fiit.mtaa.fe.movenow

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng
import retrofit2.HttpException
import stu.fiit.mtaa.fe.movenow.auth.AuthResult
import stu.fiit.mtaa.fe.movenow.auth.CreateAppUser
import stu.fiit.mtaa.fe.movenow.auth.LoginAppUser
import stu.fiit.mtaa.fe.movenow.data.Activity
import stu.fiit.mtaa.fe.movenow.data.ActivityType
import stu.fiit.mtaa.fe.movenow.data.AddActivity
import stu.fiit.mtaa.fe.movenow.data.AppUser
import stu.fiit.mtaa.fe.movenow.data.Friendship
import stu.fiit.mtaa.fe.movenow.data.FriendsActivity
import stu.fiit.mtaa.fe.movenow.models.NewMessage
import stu.fiit.mtaa.fe.movenow.models.NewNotification
import stu.fiit.mtaa.fe.movenow.data.UpdateAppUser
import stu.fiit.mtaa.fe.movenow.models.Message
import stu.fiit.mtaa.fe.movenow.screens.addscreen.data.FriendActionResult
import stu.fiit.mtaa.fe.movenow.screens.trackingscreen.TrackingUtils
import java.time.LocalDate

class MainRepository constructor(private val retroService: RetroService) {

    private val prefs: SharedPreferences
        get() = Prefs.getInstance()



    //AUTH
    suspend fun register(
        username: String,
        password: String,
        fullName: String,
        age: String
    ): AuthResult<Unit> {
        return try {
            val response = retroService.registerUser(
                CreateAppUser(
                    username = username,
                    password = password,
                    fullName = fullName,
                    age = age
                )
            )
            prefs.edit().putString("jwt", response.token).apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 400) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception){
            AuthResult.UnknownError()
        }
    }


    suspend fun logIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = retroService.authenticateUser(
                LoginAppUser(
                    username = username,
                    password = password
                )
            )
            prefs.edit().putString("jwt", response.token).apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception){
            AuthResult.UnknownError()
        }
    }

    //ACTIVITIES
    suspend fun getActivity(activityID: Long) = retroService.getActivity(activityID)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllActivities(): List<Activity> {
        val token = prefs.getString("jwt", "")
        val response = retroService.getAllActivities("Bearer $token")
        val activities = response.map { activity ->
            Activity(
                distance = activity.distance,
                duration = TrackingUtils.getFormattedTime(activity.duration),
                avgSpeed = activity.avgSpeed,
                calories = activity.calories,
                route = activity.mapRoute.split(". ")
                    .map { it.removePrefix("LatLng(").removeSuffix(")") }
                    .map { it.split(", ") }
                    .map { LatLng(it[0].toDouble(), it[1].toDouble()) },
                type = when (activity.activityType) {
                    "Walking" -> ActivityType.Walking
                    "Running" -> ActivityType.Running
                    "Cycling" -> ActivityType.Cycling
                    else -> ActivityType.Hiking
                },
                date = LocalDate.parse(activity.date)
            )
        }
        return activities
    }

    suspend fun addActivity(activity: AddActivity) {
        val token = prefs.getString("jwt", "")
        retroService.addActivity("Bearer $token", activity)
    }

    suspend fun deleteActivity(activityID: Long) = retroService.deleteActivity(activityID)

    suspend fun shareActivity(activityID: Long) = retroService.shareActivity(activityID)

    suspend fun friendsActivities(): List<FriendsActivity> {
        val token = prefs.getString("jwt", "")
        return retroService.friendsActivities("Bearer $token")
    }

    //USERS
    suspend fun getCurrentUser(): AppUser {
        val token = prefs.getString("jwt", "")
        return retroService.getCurrentUser("Bearer $token")
    }

    suspend fun updateCurrentUser(fullName: String, age: String): AppUser {
        val token = prefs.getString("jwt", "")
        return retroService.updateCurrentUser("Bearer $token", UpdateAppUser(fullName, age))
    }

    suspend fun logOut(){
        val token = prefs.getString("jwt", "")
        retroService.logOut("Bearer $token")
    }

    //FRIENDS
    suspend fun getFriends(): List<Friendship> {
        val token = prefs.getString("jwt", "")
        return retroService.getFriends("Bearer $token")
    }

    suspend fun getAddFriends(): List<AppUser> {
        val token = prefs.getString("jwt", "")
        val response = retroService.getNotFriends("Bearer $token")
        val users = response.map { user ->
            AppUser(
                username = user.username,
                age = user.age,
                fullName = user.fullName,
            )
        }
        return users
    }

    suspend fun getFriendsPending() = retroService.getFriendsPending()

    suspend fun addFriend(username: String): FriendActionResult {
        val token = prefs.getString("jwt", "")
        return try {
            retroService.addFriend("Bearer $token", username)
            FriendActionResult.Success()
        } catch (e: HttpException) {
            if (e.code() == 404) {
                FriendActionResult.NotFound()
            } else {
               FriendActionResult.UnknownError()
            }
        } catch (e: Exception){
            Log.e("AddFriendError", "Error adding friend: ${e.message}")
            FriendActionResult.UnknownError()
        }
    }

    suspend fun deleteFriendship(friendshipID: Long): FriendActionResult{
        val token = prefs.getString("jwt", "")
        return try {
            retroService.deleteFriendship("Bearer $token", friendshipID)
            FriendActionResult.Success()
        } catch (e: HttpException) {
            if (e.code() == 404) {
                FriendActionResult.NotFound()
            } else {
                FriendActionResult.UnknownError()
            }
        } catch (e: Exception){
            Log.e("DeleteFriendError", "Error deleting friend: ${e.message}")
            FriendActionResult.UnknownError()
        }
    }

    //MESSAGES
    suspend fun getMessages(friendID: Long): List<Message>{
        val token = prefs.getString("jwt", "")
        val response = retroService.getMessages("Bearer $token", friendID)
        return response
    }

    suspend fun addMessage(friendID: Long, newMessage: NewMessage){
        val token = prefs.getString("jwt", "")
        retroService.addMessage("Bearer $token", friendID, newMessage)
    }

    //NOTIFICATIONS
    suspend fun sendFcmToken(fcmToken: String){
        val token = prefs.getString("jwt", "")
        retroService.sendFcmToken(
            "Bearer $token",
            fcmToken
        )
    }
    suspend fun getNotifications(userID: Long) = retroService.getNotifications(userID)

    suspend fun addNotification(newNotification: NewNotification) = retroService.addNotification(newNotification)

    suspend fun getUnacceptedNotifications(userID: Long) = retroService.getUnacceptedNotifications(userID)
}