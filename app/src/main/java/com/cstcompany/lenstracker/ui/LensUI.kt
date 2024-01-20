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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cstcompany.lenstracker.model.EyeSide
import com.cstcompany.lenstracker.model.LensData


@Preview(showBackground = true, showSystemUi = true, apiLevel = 34)
@Composable
fun MainUiPreview() {
    val rightEye = LensData(side = EyeSide.RIGHT)
    val leftEye = LensData(side = EyeSide.LEFT)
    LensUI(snackbarHostState = SnackbarHostState(), arrayOf(rightEye, leftEye)){}
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
fun LensUI(
    snackbarHostState: SnackbarHostState,
    lensData: Array<LensData>,
    changeLens: (LensData) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /**/
        NoDataSnackbar(
            rightEyeDays = lensData[0].daysLeft, 
            leftEyeDays = lensData[1].daysLeft, 
            snackbarHostState
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LensView(
                side = EyeSide.LEFT,
                days = if(lensData[0].daysLeft < 0) 0 else lensData[0].daysLeft,
                btnColors = ButtonDefaults.buttonColors(
                    contentColor = if(lensData[0].daysLeft < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
                    containerColor = if(lensData[0].daysLeft < 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primary
                )
            ){
                changeLens(lensData[0])
            }
            Spacer(modifier = Modifier.width(20.dp))
            LensView(
                side = EyeSide.RIGHT,
                days = if(lensData[1].daysLeft < 0) 0 else lensData[1].daysLeft,
                btnColors = ButtonDefaults.buttonColors(
                    contentColor = if(lensData[1].daysLeft < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
                    containerColor = if(lensData[1].daysLeft < 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primary
                )
            ){
                changeLens(lensData[1])
            }
        }
    }
}