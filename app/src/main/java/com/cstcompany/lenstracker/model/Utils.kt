package com.cstcompany.lenstracker.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun changeLens(
    lensData: LensData,
    storageHandler: StorageHandler,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    scope.launch {
        storageHandler.resetCounter(lensData.side)
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar("${lensData.side.sideName} lens changed", duration = SnackbarDuration.Short)
    }
}

fun mockChangeLens(
    lensData: LensData,
    storageHandler: StorageHandler,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {}