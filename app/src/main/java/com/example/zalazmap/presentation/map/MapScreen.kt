package com.example.zalazmap.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zalazmap.R
import com.example.zalazmap.domain.model.Station
import com.example.zalazmap.ui.theme.AlmostWhite
import com.example.zalazmap.ui.theme.Grey
import com.example.zalazmap.ui.theme.LightGreen
import com.example.zalazmap.ui.theme.Purple
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
            MapBottomSheet(
                station = currentStation,
                bottomSheetState = bottomSheetState,
                coroutineScope = coroutineScope
            )
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        backgroundColor = AlmostWhite

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
                    color = if (station.isExplored) LightGreen.toArgb() else Purple.toArgb(),
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
                            it.hideInfoWindow()
                        }
                    },
                    station = station
                )
            }
        }
    }
}

@Composable
fun MapMarker(
    state: MarkerState,
    color: Int,
    onClick: (Marker) -> Boolean,
    onInfoWindowLongClick: (Marker) -> Unit,
    station: Station
) {
    val icon = bitmapDescriptorFromVector(
        context = LocalContext.current,
        vectorResId = R.drawable.ic_baseline_circle_24,
        color = color
    )
    MarkerInfoWindow(
        state = state,
        icon = icon,
        alpha = 0.5f,
        onClick = onClick,
        onInfoWindowLongClick = onInfoWindowLongClick,
        content = { MarkerInfoWindowContent(station = station) }
    )
}

@Composable
fun MarkerInfoWindowContent(station: Station) {
    Column(
        modifier = Modifier
            .width(260.dp)
            .background(AlmostWhite)
            .border(width = 2.dp, color = Grey)
            .padding(8.dp),
    ) {
        Text(
            text = station.title,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Направление: ${station.direction}")
        Text(text = "Турникеты: ${
            when (station.isProtected) {
                        true -> "есть"
                        false -> "нет"
                        null -> "нет данных"
                    }
        }")
        Text(text = "Обход: ${
            when (station.isBypassing) {
                        true -> "есть"
                        false -> "нет"
                        null -> "нет данных"
                    }
        }")
        if (!station.comment.isNullOrBlank()) {
            Text(text = "Комментарий: ${station.comment}")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Долгое нажатие для изменения",
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Right
        )
    }
}

