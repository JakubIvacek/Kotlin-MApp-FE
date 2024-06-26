package stu.fiit.mtaa.fe.movenow.screens.historyscreen.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d. MMMM yyyy", Locale.ENGLISH) //TODO Zmeniť podla zvoleneho jazyka
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)
}

fun formatDuration(inputDuration: String): String{
    val parts = inputDuration.split(":").map { it.toInt() }
    val hours = parts[0]
    val minutes = parts[1]
    val seconds = parts[2]

    return when {
        hours > 0 -> "${hours}h${minutes}m"
        minutes > 0 -> "${minutes}m"
        else -> "${seconds}s"
    }
}