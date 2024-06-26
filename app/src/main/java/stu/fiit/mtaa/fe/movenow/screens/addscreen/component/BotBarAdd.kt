package stu.fiit.mtaa.fe.movenow.screens.addscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel

@Composable
fun BotBarAdd(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
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
        Row() {
            OutlinedTextField(
                value = "",
                onValueChange = {
                    "name here"
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .width(300.dp),
                label = { Text("Add new friend", fontSize = 16.sp) },
                placeholder = { Text("Enter friends name") },
                singleLine = true,
                shape = RoundedCornerShape(32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                    focusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = MaterialTheme.colorScheme.tertiary
                )
            )
            IconButton(
                modifier = Modifier
                    .padding(top = 10.dp),
                onClick = {  }
            ) {
                Icon(
                    modifier = Modifier.size(67.dp),
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Delete friend",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}