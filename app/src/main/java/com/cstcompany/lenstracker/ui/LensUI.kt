package com.cstcompany.lenstracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cstcompany.lenstracker.model.EyeSide
import com.cstcompany.lenstracker.model.StorageHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainUi() {
    LensUI(snackbarHostState = SnackbarHostState())
}

@Composable
fun LensView(
    side: EyeSide,
    days: Int,
    modifier: Modifier = Modifier,
    btnModifier: Modifier = Modifier,
    btnColors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary
    ),
    resetCounter: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        HeaderText(
            text = if(side == EyeSide.LEFT) "Left Eye" else "Right Eye"
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Days wore: $days")

        Button(onClick = { resetCounter() }, modifier = btnModifier, colors = btnColors) {
            Text(text = "Change Lens")
        }
    }
}

@Composable
fun LensUI(snackbarHostState: SnackbarHostState) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val auth = FirebaseAuth.getInstance()
        val storageHandler = StorageHandler(auth.currentUser, LocalContext.current)
        val daysLeft = storageHandler.getDayCount().collectAsState(arrayOf(0, 0))
        NoDataSnackbar(daysLeft, snackbarHostState)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val coroutineScope = rememberCoroutineScope()

            LensView(
                side = EyeSide.LEFT,
                days = if(daysLeft.value[0] < 0) 0 else daysLeft.value[0],
                btnColors = ButtonDefaults.buttonColors(
                    contentColor = if(daysLeft.value[0] < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
                    containerColor = if(daysLeft.value[0] < 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primary
                )
            ){
                coroutineScope.launch {
                    run {
                        storageHandler.resetCounter(EyeSide.LEFT)
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar("Left lens changed", duration = SnackbarDuration.Short)
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            LensView(
                side = EyeSide.RIGHT,
                days = if(daysLeft.value[1] < 0) 0 else daysLeft.value[1],
                btnColors = ButtonDefaults.buttonColors(
                    contentColor = if(daysLeft.value[1] < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
                    containerColor = if(daysLeft.value[1] < 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primary
                )
            ){
                coroutineScope.launch {
                    run {
                        storageHandler.resetCounter(EyeSide.RIGHT)
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar("Right lens changed")
                    }
                }
            }
        }
    }
}