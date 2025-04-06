package com.airbender.artspace.presentor

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchBarViewModel: ViewModel() {
    val query = mutableStateOf("")
}