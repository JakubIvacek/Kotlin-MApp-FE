package stu.fiit.mtaa.fe.movenow

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import stu.fiit.mtaa.fe.movenow.auth.BiometricPromptManager
import stu.fiit.mtaa.fe.movenow.location.LocationUtils
import stu.fiit.mtaa.fe.movenow.screens.friendsscreen.FriendsScreen
import stu.fiit.mtaa.fe.movenow.screens.homescreen.HomeScreen
import stu.fiit.mtaa.fe.movenow.screens.LoginScreen
import stu.fiit.mtaa.fe.movenow.screens.PickActivityScreen
import stu.fiit.mtaa.fe.movenow.screens.historyscreen.HistoryScreen
import stu.fiit.mtaa.fe.movenow.screens.profilescreens.ProfileScreen
import stu.fiit.mtaa.fe.movenow.screens.profilescreens.ProfileUpdateScreen
import stu.fiit.mtaa.fe.movenow.screens.RegisterScreen
import stu.fiit.mtaa.fe.movenow.screens.addscreen.AddScreen
import stu.fiit.mtaa.fe.movenow.screens.chatscreen.ChatScreen
import stu.fiit.mtaa.fe.movenow.screens.trackdetailscreen.TrackDetailScreen
import stu.fiit.mtaa.fe.movenow.screens.trackingscreen.TrackingScreen
import stu.fiit.mtaa.fe.movenow.viewmodels.ChatViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.FriendsViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel
import stu.fiit.mtaa.fe.movenow.viewmodels.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController, biometricPromptManager: BiometricPromptManager){
    val viewModel: MainViewModel = viewModel()
    val userViewModel: ProfileViewModel = viewModel()
    val friendsViewModel: FriendsViewModel = viewModel()
    val chatViewModel: ChatViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    NavHost(
        navController = navController,
        startDestination = "login",
        ) {

        composable("login") {
            LoginScreen(
                biometricPromptManager = biometricPromptManager,
                onLoginClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.NavBarScreen.Home.route)
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen{
                navController.popBackStack()
                navController.navigate(Screen.NavBarScreen.Home.route)
            }
        }
        composable(Screen.NavBarScreen.Home.route){
            HomeScreen( userViewModel, navController)
        }
        composable(Screen.NavBarScreen.Friends.route){
            FriendsScreen(
                navController = navController,
                friendsViewModel = friendsViewModel,
                onChatClick = {
                    chatViewModel.setUserID(it)
                    navController.navigate("chatScreen")
                },
                onAddClick = {
                    navController.navigate("addScreen")
                })
        }
        composable(Screen.NavBarScreen.Track.route){
            PickActivityScreen(
                navController = navController,
                viewModel = viewModel,
                locationUtils = locationUtils,
                context = context,
                onActivityClick = {
                    viewModel.pickActivity(it)
                    navController.navigate("tracking"){
                        this.launchSingleTop // V stacku sa može nachadzat iba jedna location screen
                    }
                }
            )
        }
        composable("chatScreen"){
            ChatScreen(
                navController,
                chatViewModel = chatViewModel,
                viewModel,
                onBackClick = {
                    navController.navigate(Screen.NavBarScreen.Friends.route)
                }
            )
        }
        composable("tracking"){
            viewModel.location.value?.let {
                TrackingScreen(
                    viewModel,
                    it,
                    onNavigationBack = {
                        navController.navigate(Screen.NavBarScreen.Track.route)
                    },
                ){

                    navController.navigate("activityDetail")
                }
            }

        }
        composable(Screen.NavBarScreen.History.route){
            HistoryScreen(navController,viewModel){
                navController.navigate("activityDetail")
            }
        }
        composable("activityDetail"){
            TrackDetailScreen(
                viewModel = viewModel,
                onBackClickFromHistory = {
                    navController.navigate(Screen.NavBarScreen.History.route)
                },
            ) {
                navController.navigate(Screen.NavBarScreen.Track.route)
            }
        }
        composable(Screen.NavBarScreen.Profile.route){
            ProfileScreen(
                currentUser = userViewModel.state.currentUser!!,
                navController = navController,
                onUpdateClick = {
                    navController.navigate("updateScreen")
                },
                onLogOutClick = {
                    navController.navigate("login"){
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }
                    }
                    userViewModel.logOut()
                }
            )
        }
        composable("updateScreen"){
            ProfileUpdateScreen(
                onSaveClick = {
                    navController.navigate(Screen.NavBarScreen.Profile.route)
                },
                onBackClick = {
                    navController.navigate(Screen.NavBarScreen.Profile.route)
                },
                navController = navController,
                userViewModel = userViewModel
            )
        }
        composable("addScreen"){
            AddScreen(
                friendsViewModel = friendsViewModel,
                onBackClick = {
                    navController.navigate(Screen.NavBarScreen.Friends.route)
                }
            )
        }
    }
}
