package stu.fiit.mtaa.fe.movenow.screens.trackingscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bar(
    viewModel: MainViewModel,
    onNavigationBack: () -> Unit
){

    val navigationIcon : (@Composable () -> Unit) =
        {
            IconButton(onClick = { onNavigationBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title = {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp), horizontalArrangement = Arrangement.Center){
                Text(
                    text = "Move",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Now",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        navigationIcon = navigationIcon,
        actions = {
            Row (
                modifier = Modifier.width(70.dp)
            ){
                Icon(
                    painter = painterResource(id = viewModel.pickedActivity.value.icon),
                    contentDescription = "Activity Icon",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    )
}