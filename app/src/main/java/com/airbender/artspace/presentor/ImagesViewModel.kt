package com.airbender.artspace.presentor

import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbender.artspace.repository.ImagesAPI
import com.airbender.artspace.repository.PexelsResponse
import com.airbender.artspace.repository.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImagesViewModel(private val imageRepository: ImagesAPI = ImagesAPI()) : ViewModel() {
    private var photosList: MutableState<MutableList<Photo>> = mutableStateOf(emptyList<Photo>().toMutableList())
    var nextImage: MutableState<Photo> = mutableStateOf(Photo())
    var currentIndex: MutableIntState = mutableIntStateOf(1)
    var totalImages: MutableIntState = mutableIntStateOf(0)
    var currentIndexTextFieldValue: MutableState<String> =  mutableStateOf("")
    private lateinit var pexelResponse: PexelsResponse

    init {
        searchImages()
    }

    fun searchImages(
        keyword: String = "welcome",
        totalPages: Int = 1,
        perPage: Int = 80,
        orientation: String = "portrait"
    ) {
        Log.d("ImagesViewModel", "Searching images for keyword: $keyword")
        viewModelScope.launch(Dispatchers.IO) {
             pexelResponse = getImages(
                keyword,
                totalPages,
                perPage,
                orientation
            )
            withContext(Dispatchers.Main) {
                photosList.value = pexelResponse.photos
                if (photosList.value.isNotEmpty()) {
                    nextImage.value =  photosList.value[0]
                    currentIndex.intValue = 1
                    totalImages.intValue =  photosList.value.size
                }
            }
            if (pexelResponse.nextPage.isNotBlank()) loadAllImages(2, keyword)
        }
    }

    private suspend fun loadAllImages(nextPage: Int, keyword : String){
        if (pexelResponse.nextPage.isNotBlank()){
            viewModelScope.launch(Dispatchers.IO) {
                photosList.value.addAll( getImages(
                    keyword = keyword,
                    totalPages = nextPage,
                    perPage = 80,
                    orientation = "portrait"
                ).photos)
            }.join()
            totalImages.intValue = photosList.value.size
            if (photosList.value.size < 300) loadAllImages(nextPage + 1 , keyword)
            else return
        }
        else return
    }
    fun updateIndex(){
        currentIndex.intValue = currentIndexTextFieldValue.value.toIntOrNull() ?: currentIndex.intValue
        nextImage.value =  photosList.value[currentIndex.intValue - 1]
    }

    private suspend fun getImages(
        keyword: String,
        totalPages: Int,
        perPage: Int,
        orientation: String
    ): PexelsResponse {
        return imageRepository.getImages(
            keyword,
            totalPages,
            perPage,
            orientation
        )
    }

    fun setNextImage() {
        if (photosList.value.isNotEmpty() && currentIndex.intValue < photosList.value.size - 1) {
            currentIndex.value += 1
            nextImage.value = photosList.value[currentIndex.intValue]
        }
    }

    fun setPreviousImage() {
        if (photosList.value.isNotEmpty() && currentIndex.intValue > 0) {
            currentIndex.value -= 1
            nextImage.value = photosList.value[currentIndex.intValue]
        }
    }
}