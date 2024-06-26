package stu.fiit.mtaa.fe.movenow.screens.historyscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.data.Activity
import stu.fiit.mtaa.fe.movenow.screens.historyscreen.utils.formatDate
import stu.fiit.mtaa.fe.movenow.screens.historyscreen.utils.formatDuration
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel

@Composable
fun HistoryItem(
    activity: Activity,
    viewModel: MainViewModel,
    onDetailClick: () -> Unit
) {

    Column (
        modifier = Modifier.padding(vertical = 5.dp)
    ){
        Text(
            text = formatDate(activity.date.toString()),
            modifier = Modifier.padding(start = 15.dp),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Do viewModel si pridame vybranu activitu a presunieme sa na stranku detailu
                    viewModel.pickDetailActivity(activity)
                    onDetailClick()
                           },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = activity.type.icon),
                    contentDescription = activity.type.name + "Activity",
                    modifier = Modifier
                        .size(35.dp)
                        .weight(1f),
                    tint = MaterialTheme.colorScheme.tertiary
                )

                VerticalDivider()

                ActivityInfo(
                    iconResId = R.drawable.duration_24,
                    text = formatDuration(activity.duration),
                    modifier = Modifier.weight(2f)
                )

                VerticalDivider()

                ActivityInfo(
                    iconResId = R.drawable.baseline_location_pin_24,
                    text = activity.distance,
                    modifier = Modifier.weight(2f)
                )

                VerticalDivider()

                ActivityInfo(
                    iconResId = R.drawable.calories_burned_24,
                    text = activity.calories,
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}

@Composable
fun ActivityInfo(iconResId: Int, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = when (iconResId) {
                R.drawable.duration_24 -> Color(0xFFFF8000)
                R.drawable.baseline_location_pin_24 -> Color(0xFF008F02)
                else -> Color(0xFFFF0000)
            }
        )

        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(2.dp)
            .height(40.dp)
            .background(Color.Black)
    )
}