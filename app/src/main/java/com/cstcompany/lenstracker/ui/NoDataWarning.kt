package com.cstcompany.lenstracker.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State

@Composable
fun NoDataSnackbar(daysLeft: State<Array<Int>>, snackbarHostState: SnackbarHostState) {
    if(daysLeft.value[0] < 0 && daysLeft.value[1] < 0){
        LaunchedEffect(true){
            snackbarHostState.showSnackbar("There is no left/right change date set up", duration = SnackbarDuration.Long)
        }
    }else if(daysLeft.value[0] < 0 && daysLeft.value[1] >= 0) {
        LaunchedEffect(true) {
            snackbarHostState.showSnackbar(
                "There is no left change date set up",
                duration = SnackbarDuration.Long
            )
        }
    }else if(daysLeft.value[0] >= 0 && daysLeft.value[1] < 0) {
        LaunchedEffect(true) {
            snackbarHostState.showSnackbar(
                "There is no right change date set up",
                duration = SnackbarDuration.Long
            )
        }
    }
}