package stu.fiit.mtaa.fe.movenow

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import stu.fiit.mtaa.fe.movenow.auth.BiometricPromptManager
import stu.fiit.mtaa.fe.movenow.notifications.requestNotificationPermission
import stu.fiit.mtaa.fe.movenow.ui.theme.MoveNowTheme

class MainActivity : AppCompatActivity() {

    private val biometricPromptManager by lazy {
        BiometricPromptManager(this)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission(this)
        Prefs.init(this)
        setContent {
            val navController = rememberNavController()
            MoveNowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        navController = navController,
                        biometricPromptManager = biometricPromptManager
                    )
                }
            }
        }
    }
}

