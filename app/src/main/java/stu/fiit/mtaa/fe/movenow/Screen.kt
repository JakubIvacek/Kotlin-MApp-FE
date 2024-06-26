package stu.fiit.mtaa.fe.movenow

import androidx.annotation.DrawableRes


sealed class Screen(
    val title: String,
    val route: String
) {

    sealed class NavBarScreen(
        title: String,
        @DrawableRes val selectedIcon: Int,
        @DrawableRes val unselectedIcon: Int,
        route: String
    ): Screen(title,route){
        data object Home: NavBarScreen(
            "Home",
            R.drawable.baseline_home_24,
            R.drawable.outline_home_24,
            "home"
        )

        data object Friends: NavBarScreen(
            "Friends",
            R.drawable.baseline_people_alt_24,
            R.drawable.outline_people_alt_24,
            "friends"
        )

        data object Track: NavBarScreen(
            "Track",
            R.drawable.baseline_play_circle_24,
            R.drawable.outline_play_circle_24,
            "track"
        )

        data object History: NavBarScreen(
            "History",
            R.drawable.baseline_data_thresholding_24,
            R.drawable.outline_data_thresholding_24,
            "history"
        )

        data object Profile: NavBarScreen(
            "Profile",
            R.drawable.baseline_account_circle_24,
            R.drawable.outline_account_circle_24,
            "profile"
        )
    }

}

val screensInBottomBar = listOf(
    Screen.NavBarScreen.Home,
    Screen.NavBarScreen.Friends,
    Screen.NavBarScreen.Track,
    Screen.NavBarScreen.History,
    Screen.NavBarScreen.Profile
)