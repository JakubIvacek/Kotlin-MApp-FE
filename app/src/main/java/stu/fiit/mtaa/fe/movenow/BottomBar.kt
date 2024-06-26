package stu.fiit.mtaa.fe.movenow

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavController) {

    // Allow us to find out on which screen we are currently
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        screensInBottomBar.forEach { item ->
            val title = when (item) {
                is Screen.NavBarScreen.Home -> stringResource(id = R.string.home)
                is Screen.NavBarScreen.Friends -> stringResource(id = R.string.friends)
                is Screen.NavBarScreen.Track -> stringResource(id = R.string.track)
                is Screen.NavBarScreen.History -> stringResource(id = R.string.history)
                is Screen.NavBarScreen.Profile -> stringResource(id = R.string.profile)
            }
            val isSelected = currentRoute == item.route
            val tint = if(isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
            val icon = if (isSelected) painterResource(id = item.selectedIcon)
            else painterResource(id = item.unselectedIcon)
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                label = { androidx.compose.material.Text(text = title, color = tint) },
                icon = {
                    Icon(
                        painter = icon,
                        contentDescription = item.title,
                        tint = tint
                    )
                }
            )
        }
    }
}