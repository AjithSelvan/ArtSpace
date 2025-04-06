package com.airbender.artspace

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCurrentImageDialog(
    indexValue: String = "",
    totalImages: String = "0",
    onDismissRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    onImageIndexValueChange: (String) -> Unit = {}
) {
    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .height(250.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Select Values Within Range",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(12.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        textValue = indexValue,
                        onValueChange = onImageIndexValueChange,
                        label = R.string.select_image,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        leadingIcon = Icons.Default.Search,
                        contentDescription = null,
                    )
                    Text(
                        text = "/$totalImages",
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    FilledTonalButton(
                        shape = ButtonDefaults.elevatedShape,
                        onClick = onCancel,
                        modifier = Modifier
                            .fillMaxHeight()
                            //.size(width = 125.dp, height = 40.dp)
                            .wrapContentHeight(Alignment.Bottom),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Text(
                            text = "NO", modifier = Modifier
                            //.fillMaxHeight()
                            //.wrapContentHeight(Alignment.Center)
                        )
                    }
                    FilledTonalButton(
                        shape = ButtonDefaults.elevatedShape,
                        onClick = onConfirm,
                        modifier = Modifier
                            .fillMaxHeight()
                            //.size(width = 125.dp, height = 40.dp)
                            .wrapContentHeight(Alignment.Bottom),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Text(
                            text = "YES", modifier = Modifier
                            //.fillMaxHeight()
                            //.wrapContentHeight(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OutlinedTextField(
    textValue: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    leadingIcon: ImageVector,
    @StringRes contentDescription: Int?,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.OutlinedTextField(
        value = textValue,
        maxLines = 1,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(label),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp
            )
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .padding(8.dp)
            .wrapContentWidth(align = Alignment.Start)
            .size(width = 170.dp, height = 70.dp),
        leadingIcon = {
            Icon(
                leadingIcon,
                contentDescription = contentDescription.toString()
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DialogPreview() {
    EditCurrentImageDialog()
}