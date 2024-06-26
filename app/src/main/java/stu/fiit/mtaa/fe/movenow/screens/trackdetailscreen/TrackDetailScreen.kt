package stu.fiit.mtaa.fe.movenow.screens.trackdetailscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.screens.historyscreen.utils.formatDate
import stu.fiit.mtaa.fe.movenow.screens.trackdetailscreen.components.ActivityStats
import stu.fiit.mtaa.fe.movenow.screens.trackdetailscreen.components.TrackMap
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun TrackDetailScreen(
    viewModel: MainViewModel,
    onBackClickFromHistory: () -> Unit,
    onBackClickFromTracking: () -> Unit
){

    val pickedActivity = viewModel.pickedDetailActivity.value
    if (pickedActivity != null)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
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
                Box (
                    modifier = Modifier.fillMaxSize()
                ){
                    IconButton(
                        onClick = viewModel.fromHistoryScreen.value
                            .let { if (it) onBackClickFromHistory else onBackClickFromTracking },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Button",
                            modifier = Modifier.size(35.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Text(
                        text = when(pickedActivity.type.name){
                            "WALKING" -> stringResource(id = R.string.walking)
                            "RUNNING" -> stringResource(id = R.string.running)
                            "CYCLING" -> stringResource(id = R.string.cycling)
                            else -> stringResource(id = R.string.hiking)
                        },
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = formatDate(pickedActivity.date.toString()),
                modifier = Modifier.padding(start = 15.dp, top = 1.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            TrackMap(modifier = Modifier
                .weight(1.3f)
                .padding(vertical = 4.dp, horizontal = 3.dp),
                pickedActivity
            )

            Spacer(modifier = Modifier.height(10.dp))

            ActivityStats(modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp, end = 3.dp, bottom = 70.dp),
                pickedActivity
            )
        }
    }
}
