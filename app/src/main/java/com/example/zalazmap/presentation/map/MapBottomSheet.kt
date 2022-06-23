package com.example.zalazmap.presentation.map

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zalazmap.domain.model.Station

@Composable
fun MapBottomSheet(
    station: Station?,
    viewModel: MapViewModel = viewModel()
) {

    val protectionCheckboxState = remember { mutableStateOf( false) }
    val passCheckboxState = remember { mutableStateOf(false) }
    val exploreCheckboxState = remember { mutableStateOf(false) }
    val commentState = remember { mutableStateOf("") }


    LaunchedEffect(key1 = station) {
        protectionCheckboxState.value = station?.isProtected ?: false
        passCheckboxState.value = station?.isBypassing ?: false
        exploreCheckboxState.value = station?.isExplored ?: false
        commentState.value = station?.comment ?: ""
    }


    Column(modifier = Modifier
        .height(500.dp)
        .fillMaxWidth()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        if (station == null) {
            Text(text = "Ошибка")
        } else {
            Text(
                text = station.title,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Checkbox(
                        checked = protectionCheckboxState.value,
                        onCheckedChange = { protectionCheckboxState.value = it }
                    )
                    Text(text = "Турникеты")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Checkbox(
                        checked = passCheckboxState.value,
                        onCheckedChange = { passCheckboxState.value = it }
                    )
                    Text(text = "Обход")
                }
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                value = commentState.value,
                onValueChange = { commentState.value = it },
                placeholder = { Text(text = "Комментарий") },
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions.Default,

            )
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.height(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = exploreCheckboxState.value,
                    onCheckedChange = {
                        exploreCheckboxState.value = it
                    }
                )
                Text(text = "Были здесь?")
            }
            Button(onClick = {
                viewModel.onEvent(MapEvent.OnSaveStationButtonClick(
                    station.copy(
                        isProtected = protectionCheckboxState.value,
                        isBypassing = passCheckboxState.value,
                        isExplored = exploreCheckboxState.value,
                        comment = commentState.value.ifBlank { null }
                    )
                ))

            }) {
                Text(text = "Сохранить")
            }
        }
    }
}