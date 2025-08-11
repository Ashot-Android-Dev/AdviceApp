package com.example.advice.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.advice.R


@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,

    ) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),

        title = {
            Text(text = dialogTitle,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold)
        },
        text = {
            Text(text = dialogText,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
                )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(R.string.yes),
                    color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    stringResource(R.string.no),
                    color = Color.Red)
            }
        }
    )
}