package stu.fiit.mtaa.fe.movenow.screens.historyscreen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import stu.fiit.mtaa.fe.movenow.BottomBar
import stu.fiit.mtaa.fe.movenow.TopAppBar
import stu.fiit.mtaa.fe.movenow.viewmodels.MainViewModel
import androidx.navigation.NavController
import stu.fiit.mtaa.fe.movenow.ConnectionChecker
import stu.fiit.mtaa.fe.movenow.R
import stu.fiit.mtaa.fe.movenow.WindowInfo
import stu.fiit.mtaa.fe.movenow.rememberWindowInfo
import stu.fiit.mtaa.fe.movenow.screens.historyscreen.components.HistoryItem


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: MainViewModel,
    onDetailClick: () -> Unit
){

    val viewState by viewModel.state
    val context = LocalContext.current
    val noInternet = stringResource(id = R.string.noConnection)
    val windowInfo = rememberWindowInfo()
    LaunchedEffect(key1 = Unit) {
        if (ConnectionChecker.isConnectionAvailable(context)){
            viewModel.fetchActivities()
        }else{
            Toast.makeText(context, noInternet,  Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController = navController)
        },
        topBar = {

            TopAppBar(title = stringResource(id = R.string.history))
        }
    ) {
        when(viewState.loading){
            true -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            false -> {
                if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(it)
                    ) {
                        items(viewState.list){ activity ->
                            HistoryItem(activity = activity,viewModel = viewModel, onDetailClick = onDetailClick)
                        }
                    }
                }else(
                        LazyRow(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(it)
                        ) {
                            items(viewState.list){ activity ->
                                HistoryItem(activity = activity,viewModel = viewModel, onDetailClick = onDetailClick)
                            }
                        }
                )
            }
        }
    }
}



