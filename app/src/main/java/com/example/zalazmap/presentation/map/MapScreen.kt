package com.example.zalazmap.presentation.map

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zalazmap.R
import com.example.zalazmap.domain.model.Station
import com.example.zalazmap.ui.theme.Green800
import com.example.zalazmap.ui.theme.Purple700
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel()
) {

    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val coroutineScope = rememberCoroutineScope()
    var currentStation: Station? by remember {
        mutableStateOf(null)
    }

    // Moscow
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(55.751244, 37.618423),
            10f
        )
    }

    BottomSheetScaffold (
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            MapBottomSheet(station = currentStation)
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        backgroundColor = (Color(245, 234, 226))

    ) {

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
                    color = if (station.isExplored) Green800.toArgb() else Purple700.toArgb(),
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
                    },
                    onInfoWindowLongClick = {
                        currentStation = station
                        coroutineScope.launch {
                            bottomSheetState.expand()
                            viewModel.onEvent(MapEvent.OnInfoWindowLongClick(station))
                        }

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
    color: Int,
    onClick: (Marker) -> Boolean,
    onInfoWindowLongClick: (Marker) -> Unit,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId, color
    )
    MarkerInfoWindow(
        state = state,
        title = title,
        snippet = snippet,
        icon = icon,
        alpha = 0.5f,
        onClick = onClick,
        onInfoWindowLongClick = onInfoWindowLongClick
    ) {
        Column (
            modifier = Modifier
                .width(260.dp)
                .background(Color(245, 227, 215))
                .padding(8.dp),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = snippet)
        }
    }
}

