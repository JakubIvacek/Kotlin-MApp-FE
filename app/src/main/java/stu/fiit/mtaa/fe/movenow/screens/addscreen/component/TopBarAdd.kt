package stu.fiit.mtaa.fe.movenow.screens.addscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stu.fiit.mtaa.fe.movenow.R



@Composable
fun TopBarAdd(
    onBackClick: () -> Unit,
) {
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
        Row{
            IconButton(
                modifier = Modifier
                    .padding(end = 15.dp, top = 16.dp, start = 22.dp),
                onClick = onBackClick
            ) {
                Icon(
                    modifier = Modifier.size(55.dp),
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Delete friend",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            Spacer(modifier = Modifier.width(45.dp))
            Column(Modifier.padding(top = 22.dp)){
                Text(text = stringResource(id = R.string.addFriend),
                    style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.secondary
                )
            }


        }
    }
}