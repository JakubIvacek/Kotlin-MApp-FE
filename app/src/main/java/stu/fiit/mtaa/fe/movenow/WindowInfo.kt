package stu.fiit.mtaa.fe.movenow

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo{
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            else -> WindowInfo.WindowType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
    )
}


data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenWidth: Dp,
) {
    sealed class WindowType{
        object Compact: WindowType();
        object Expanded: WindowType();
    }
}