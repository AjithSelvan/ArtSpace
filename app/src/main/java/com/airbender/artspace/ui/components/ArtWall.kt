package com.airbender.artspace.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbender.artspace.repository.Photo

@Composable
fun ArtWall(
    innerPadding: PaddingValues = PaddingValues(),
    photo: Photo = Photo(),
    searchQuery: String = "",
    currentImage: Int = 0,
    totalImages: Int = 0,
    rememberedCurrentImageIndex: String = "",
    rememberedShowSheetState: Boolean = false,
    onNextClick: () -> Unit = {},
    onPreviousClick: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onImageIndexValueChange: (String) -> Unit = {},
    onIndexConfirm: () -> Unit = {},
    changeShowSheetState: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(photo,changeShowSheetState, modifier )
            ImagesCountBadge(
                currentImage,
                totalImages,
                rememberedCurrentImageIndex,
                onImageIndexValueChange,
                onIndexConfirm
            )
            //ArtistDetails(photo)
        }
        SimpleSearchBar(searchQuery, onQueryChange = onQueryChange, onSearch = onSearch)
        Buttons(onNextClick, onPreviousClick)
    }
    ArtistBottomSheet(
        rememberedShowSheetState = rememberedShowSheetState,
        photo = photo,
        modifier = modifier,
        changeShowSheetState = changeShowSheetState
    )
}