package stu.fiit.mtaa.fe.movenow

import android.content.SharedPreferences
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import stu.fiit.mtaa.fe.movenow.models.Activity
import stu.fiit.mtaa.fe.movenow.data.AddActivity
import stu.fiit.mtaa.fe.movenow.auth.CreateAppUser
import stu.fiit.mtaa.fe.movenow.auth.LoginAppUser
import stu.fiit.mtaa.fe.movenow.models.Message
import stu.fiit.mtaa.fe.movenow.models.NewMessage
import stu.fiit.mtaa.fe.movenow.models.NewNotification
import stu.fiit.mtaa.fe.movenow.models.Notification
import stu.fiit.mtaa.fe.movenow.auth.Token
import stu.fiit.mtaa.fe.movenow.data.ActivityResponse
import stu.fiit.mtaa.fe.movenow.data.AddFriend
import stu.fiit.mtaa.fe.movenow.data.AppUser
import stu.fiit.mtaa.fe.movenow.data.Friendship
import stu.fiit.mtaa.fe.movenow.data.FriendsActivity
import stu.fiit.mtaa.fe.movenow.data.UpdateAppUser

interface RetroService {

    val prefs: SharedPreferences
        get() = Prefs.getInstance()

    companion object {
        private var retrofitService: RetroService? = null
        fun getInstance() : RetroService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetroService::class.java)
            }
            return retrofitService!!
        }

    }
    // ACTIVITIES
    @GET("/activities/{activityID}")
    suspend fun getActivity(@Path("activityID") activityID: Long) : Response<Activity>

    @GET("/activities")
    suspend fun getAllActivities(
        @Header("Authorization") token: String
    ): List<ActivityResponse>

    @POST("/activities")
    suspend fun addActivity(
        @Header("Authorization") token: String = "Bearer ${prefs.getString("jwt", "")}",
        @Body activity: AddActivity
    ): Response<Activity>

    @DELETE("/activities/{activityID}")
    suspend fun deleteActivity(@Path("activityID") activityID: Long): Response<Void>

    @PUT("/share/{activityID}")
    suspend fun shareActivity(@Path("activityID") activityID: Long): Response<Void>

    @GET("/activities/friends")
    suspend fun friendsActivities(
        @Header("Authorization") token: String = "Bearer ${prefs.getString("jwt", "")}"
    ): List<FriendsActivity>

    // USERS
    @GET("/users/me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): AppUser

    @PUT("/users")
    suspend fun updateCurrentUser(
        @Header("Authorization") token: String,
        @Body updateUser: UpdateAppUser
    ): AppUser

    @PUT("/users/logout")
    suspend fun logOut(
        @Header("Authorization") token: String
    ): Response<Void>

    // AUTH
    @POST("/auth/register")
    suspend fun registerUser(@Body request: CreateAppUser): Token

    @POST("/auth/authenticate")
    suspend fun authenticateUser(@Body request: LoginAppUser): Token

    // FRIENDS
    @GET("/friends")
    suspend fun getFriends(
        @Header("Authorization") token: String
    ): List<Friendship>

    @GET("/friends/not-friends")
    suspend fun getNotFriends(
        @Header("Authorization") token: String
    ): List<AddFriend>

    @GET("/friends/pending")
    suspend fun getFriendsPending(): Response<List<AppUser>>

    @POST("/friends/add/{username}")
    suspend fun addFriend(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<Void>

    @DELETE("/friends/{friendshipID}")
    suspend fun  deleteFriendship(
        @Header("Authorization") token: String,
        @Path("friendshipID") friendshipID: Long
    ): Response<Void>

    //MESSAGES
    @GET("/messages/{friendID}")
    suspend fun  getMessages( @Header("Authorization") token: String = "Bearer ${prefs.getString("jwt", "")}",
        @Path("friendID") friendID: Long): List<Message>

    @POST("/messages/{friendID}")
    suspend fun  addMessage( @Header("Authorization") token: String = "Bearer ${prefs.getString("jwt", "")}",
        @Path("friendID") friendID: Long, @Body newMessage: NewMessage): Response<Void>

    //NOTIFICATIONS
    @PUT("/notifications/fcm")
    suspend fun sendFcmToken(
        @Header("Authorization") token: String,
        @Body fcmToken: String
    ): Response<Void>

    @GET("/notifications/{userID}")
    suspend fun  getNotifications(@Path("userID") userID: Long): Response<Notification>

    @POST("/notifications")
    suspend fun addNotification(@Body newNotification: NewNotification): Response<Notification>

    @GET("/notifications/requests/{userID}")
    suspend fun getUnacceptedNotifications(@Path("userID") userID: Long): Response<Notification>


}