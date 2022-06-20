package com.example.zalazmap.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.zalazmap.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    // Moscow
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(55.751244, 37.618423),
            10f
        )
    }

    Scaffold (scaffoldState = scaffoldState) {

        GoogleMap (
            modifier = Modifier.fillMaxSize(),
            properties = viewModel.state.properties,
            cameraPositionState = cameraPositionState,
        ) {
            viewModel.state.stations.forEach { station ->
                val stationState = rememberMarkerState(
                    position = LatLng(station.latitude, station.longitude)
                )
                MapMarker(
                    state = stationState,
                    title = station.title,
                    snippet = "Направление: ${station.direction}" +
                            "\nТурникеты: ${ 
                                when (station.isProtected) {
                                    true -> "есть"
                                    false -> "нет"
                                    null -> "нет данных"
                                } 
                            }\nОбход: ${
                                when (station.isBypassing) {
                                    true -> "есть"
                                    false -> "нет"
                                    null -> "нет данных"
                                }
                            } ${
                                if (station.comment.isNullOrBlank()) "" 
                                else "\nКомментарий: ${station.comment}"
                            }",
                    context = LocalContext.current,
                    iconResourceId = R.drawable.ic_baseline_circle_24,
                    onClick = {
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newCameraPosition(
                                    CameraPosition.fromLatLngZoom(
                                        stationState.position,
                                        15f
                                    )
                                ),
                                durationMs = 800
                            )
                            it.showInfoWindow()
                        }
                        true
                    }
                )
            }
        }
    }
}

@Composable
fun MapMarker(
    context: Context,
    state: MarkerState,
    title: String,
    snippet: String,
    onClick: (Marker) -> Boolean,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId
    )
    MarkerInfoWindow(
        state = state,
        title = title,
        snippet = snippet,
        icon = icon,
        alpha = 0.5f,
        onClick = onClick
    ) {
        Column (
            modifier = Modifier
                .width(260.dp)
                .background(Color(245, 227, 215))
                .padding(8.dp),
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = snippet)
        }
    }
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}