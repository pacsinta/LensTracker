package com.cstcompany.lenstracker.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun NoDataSnackbar(
    rightEyeDays: Int,
    leftEyeDays: Int,
    snackbarHostState: SnackbarHostState
) {
    if (rightEyeDays < 0 && leftEyeDays < 0) {
        LaunchedEffect(true) {
            snackbarHostState.showSnackbar(
                "There is no left/right change date set up",
                duration = SnackbarDuration.Long
            )
        }
    } else if (leftEyeDays < 0) {
        LaunchedEffect(true) {
            snackbarHostState.showSnackbar(
                "There is no left change date set up",
                duration = SnackbarDuration.Long
            )
        }
    } else if (rightEyeDays < 0) {
        LaunchedEffect(true) {
            snackbarHostState.showSnackbar(
                "There is no right change date set up",
                duration = SnackbarDuration.Long
            )
        }
    }
}