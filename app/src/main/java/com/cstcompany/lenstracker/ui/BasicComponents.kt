package com.cstcompany.lenstracker.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        fontSize = 30.sp
    )
}