package com.cstcompany.lenstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.cstcompany.lenstracker.model.EyeSide
import com.cstcompany.lenstracker.model.LensData
import com.cstcompany.lenstracker.model.StorageHandler
import com.cstcompany.lenstracker.model.changeLens
import com.cstcompany.lenstracker.ui.LensUI
import com.cstcompany.lenstracker.ui.theme.LensTrackerTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val storageHandler = StorageHandler(currentUser, this)
        setContent {
            val lensData = remember{ mutableStateListOf(LensData(side = EyeSide.RIGHT), LensData(side = EyeSide.LEFT)) }
            LaunchedEffect(true)
            {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        storageHandler.getDayCount().collectLatest {
                            lensData[0].daysLeft = it[0]
                            lensData[0].daysLeft = it[1]
                        }
                    }
                }
            }

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
                        }
                    ) { padding ->
                        SplashScreen(Modifier.padding(padding), currentUser, snackbarHostState, arrayOf(
                            lensData[0], lensData[1]
                        ), storageHandler)
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    currentUser: FirebaseUser? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    lensData: Array<LensData>,
    storageHandler: StorageHandler,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (currentUser == null) {
            LensUI(snackbarHostState, lensData, storageHandler,  ::changeLens)
        }
    }
}