package stu.fiit.mtaa.fe.movenow.data

import com.google.android.gms.maps.model.LatLng

data class Post(
    val owner_username: String,
    val distance: String,
    val duration: String,
    val calories: String,
    val avgSpeed: String,
    val mapRoute: List<LatLng>,
    val type: String,
    )

// Dummy Data
//val posts = listOf(
//    Post(
//        owner_username = "Jožko Mrkvička",
//        type = "BEH",
//        distance = "5",
//        duration = "2:12:54",
//        mapRoute = listOf(
//            LatLng(37.7749, -122.4194),
//            LatLng(37.7750, -122.4195),
//            LatLng(37.7751, -122.4196),
//            // More LatLng instances...
//        )
//    ),
//    Post(
//        owner_username = "Boris Novak",
//        type = "CYKLISTIKA",
//        distance = "28",
//        duration = "4:45:53",
//        mapRoute =  listOf(
//            LatLng(37.7749, -122.4194),
//            LatLng(37.7750, -122.4195),
//            LatLng(37.7751, -122.4196),
//            // More LatLng instances...
//        )
//    ),
//    Post(
//        owner_username = "Dušan Vojtko",
//        type = "TURISTIKA",
//        distance = "10",
//        duration = "3:01:32",
//        mapRoute =  listOf(
//            LatLng(37.7749, -122.4194),
//            LatLng(37.7750, -122.4195),
//            LatLng(37.7751, -122.4196),
//            // More LatLng instances...
//        )
//    )
//)