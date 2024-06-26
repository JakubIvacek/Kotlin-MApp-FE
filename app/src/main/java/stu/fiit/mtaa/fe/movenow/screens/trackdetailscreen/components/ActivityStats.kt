package stu.fiit.mtaa.fe.movenow.screens.trackdetailscreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.data.Activity

@Composable
fun ActivityStats(
    modifier: Modifier,
    activity: Activity
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface)
    ) {
        Text(
            text = stringResource(id = R.string.trainingDetails),
            modifier = Modifier.padding(start = 30.dp, top = 20.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 40.dp, end = 40.dp, top = 20.dp, bottom = 25.dp)
        ){
            Row (
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            ) {
                Column (
                    modifier = Modifier.weight(1f)
                ){
                    Text(
                        text = stringResource(id = R.string.duration),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = activity.duration,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ){
                    Text(
                        text = stringResource(id = R.string.avgSpeed),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = activity.avgSpeed + " km/h",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            Row (
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ){
                Column (
                    modifier = Modifier.weight(1f)
                ){
                    Text(
                        text = stringResource(id = R.string.calories),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = activity.calories + " kcal",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = 10.dp
                        )
                ){
                    Text(
                        text = stringResource(id = R.string.distance),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = activity.distance + " km",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}