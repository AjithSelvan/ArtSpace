package com.airbender.artspace.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImagesCountBadge(
    currentImage: Int = 0,
    totalImages: Int = 0,
    currentImageIndex: String = "",
    updateCurrentImageIndex: (String) -> Unit,
    onIndexConfirm: () -> Unit = {}
) {
    var showDetails by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BadgedBox(
            badge = {},
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .clickable {
                    showDetails = !showDetails
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Info,
                    contentDescription = "Email",
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier.size(16.dp)
                )
                Text(text = "${currentImage}/$totalImages")
            }
        }
    }
    if (showDetails) EditCurrentImageDialog(
        onDismissRequest = { showDetails = false },
        onImageIndexValueChange = updateCurrentImageIndex,
        onConfirm = {
            showDetails = false
            onIndexConfirm()
        },
        onCancel = { showDetails = false },
        indexValue = currentImageIndex,
        totalImages = totalImages.toString()
    )
}