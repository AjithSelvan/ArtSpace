package com.airbender.artspace.presentor

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UIViewModel: ViewModel() {
    val query = mutableStateOf("")
    val showBottomSheet = mutableStateOf(false)
}