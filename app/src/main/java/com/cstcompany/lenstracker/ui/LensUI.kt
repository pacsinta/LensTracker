package com.cstcompany.lenstracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cstcompany.lenstracker.model.EyeSide
import com.cstcompany.lenstracker.model.StorageHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

@Composable
fun LensUI() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val storageHandler = StorageHandler(null, LocalContext.current)
        var daysLeft = remember{ mutableStateListOf(storageHandler.getDayCount()) }

        LensView(EyeSide.LEFT, 30){
            coroutineScope.launch { {
                storageHandler.resetCounter()
                daysLeft[0] = storageHandler.getDayCount()
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        LensView(EyeSide.RIGHT, 30, storageHandler::resetCounter)
    }
}

@Composable
fun LensView(side: EyeSide, days: Int, resetCounter: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(
            text = if(side == EyeSide.LEFT) "Left Eye" else "Right Eye"
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Days left: $days")

        Button(onClick = { resetCounter() }) {
            Text(text = "Change Lens")
        }
    }
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        fontSize = 30.sp
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainUi() {
    LensUI()
}