package com.airbender.artspace.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Buttons(onNextClick: () -> Unit, onPreviousClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .fillMaxHeight()
            .wrapContentHeight(Alignment.Bottom)
    ) {
        OutlinedButton(
            shape = ButtonDefaults.elevatedShape,
            onClick = onPreviousClick,
            modifier = Modifier
                .fillMaxHeight()
                .size(width = 125.dp, height = 40.dp)
                .wrapContentHeight(Alignment.Bottom),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 12.dp
            )
        ) {
            Text(
                text = "Previous", modifier = Modifier
                //.fillMaxHeight()
                // .wrapContentHeight(Alignment.Bottom)
            )
        }
        OutlinedButton(
            shape = ButtonDefaults.elevatedShape,
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxHeight()
                .size(width = 125.dp, height = 40.dp)
                .wrapContentHeight(Alignment.Bottom),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 12.dp
            )
        ) {
            Text(
                text = "Next", modifier = Modifier
                //.fillMaxHeight()
                //.wrapContentHeight(Alignment.Center)
            )
        }
    }
}