package stu.fiit.mtaa.fe.movenow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBar(title: String, userFullName: String? = null){

    val topAppBarHeight = 80.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(topAppBarHeight)
            .drawWithContent {
                drawContent()

                val shadowColor = Color(0, 0, 0, alpha = 40)
                val shadowHeight = 2.dp

                drawRect(
                    color = shadowColor,
                    topLeft = Offset(0f, size.height - shadowHeight.toPx()),
                    size = Size(size.width, shadowHeight.toPx())
                )
            }
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        when (title) {
            "Home" -> HomeTop(userFullName ?: "<placeholder>")
            else -> {
                Row (
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = title,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}


@Composable
fun HomeTop(userFullName: String){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Move",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Now",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
        Text(
            text = stringResource(R.string.welcomeBack) + " $userFullName",
            color = Color.Gray,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}