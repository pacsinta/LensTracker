package com.cstcompany.lenstracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cstcompany.lenstracker.model.EyeSide

@Composable
fun LensUI() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Left Eye")
        Text(text = "Right Eye")
    }
}

@Composable
fun LensView(side: EyeSide) {
    Row() {
        Text(text = if(side == EyeSide.LEFT) "Left Eye" else "Right Eye")

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Change Lens")
        }
    }
}