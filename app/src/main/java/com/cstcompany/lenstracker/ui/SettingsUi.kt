package com.cstcompany.lenstracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cstcompany.lenstracker.model.EyeSide
import com.cstcompany.lenstracker.ui.theme.LensTrackerTheme

@Preview
@Composable
fun SettingsUiPreview() {
    SettingsUi(){}
}
@Composable
fun SettingsUi(
    changeUiCallback: (Int) -> Unit
) {
    LensTrackerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                topBar = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ){
                        IconButton(onClick = { changeUiCallback(0) }) {
                            // settings icon
                            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Settings")
                        }
                    }
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(top = 55.dp)
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    ExpirationSetter(EyeSide.RIGHT)
                    ExpirationSetter(EyeSide.LEFT)
                }
            }
        }
    }
}

@Composable
fun ExpirationSetter(side: EyeSide) {
    Row(
        Modifier.padding(bottom = 10.dp, top = 10.dp, start = 10.dp, end = 10.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(if(side == EyeSide.RIGHT) "Right expiration date: " else "Left expiration date: ", fontSize = 30.sp)

        var expirationDate by remember{ mutableIntStateOf(30) }
        TextField(
            value = expirationDate.toString(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                expirationDate = it.toInt()
            },
            modifier = Modifier.width(54.dp)
        )
    }
}