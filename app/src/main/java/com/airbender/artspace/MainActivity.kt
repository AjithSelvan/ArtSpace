package com.airbender.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.airbender.artspace.presentor.ImagesViewModel
import com.airbender.artspace.presentor.UIViewModel
import com.airbender.artspace.ui.components.ArtWall
import com.airbender.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                val imagesViewModel by viewModels<ImagesViewModel>()
                val uiViewModel by viewModels<UIViewModel>()
                val rememberedNextImage by remember { imagesViewModel.nextImage }
                var rememberedSearchValue by remember { uiViewModel.query }
                val rememberedTotalImages by remember { imagesViewModel.totalImages }
                val rememberedCurrentImage by remember { imagesViewModel.currentIndex }
                var rememberedCurrentImageIndex by remember { imagesViewModel.currentIndexTextFieldValue }
                var rememberedShowSheetState by remember { uiViewModel.showBottomSheet }
                Scaffold(
                    topBar = {
                        com.airbender.artspace.ui.components.TopAppBar()
                    },
                ) { innerPadding ->
                    ArtWall(
                        innerPadding = innerPadding,
                        photo = rememberedNextImage,
                        totalImages = rememberedTotalImages,
                        currentImage = rememberedCurrentImage,
                        searchQuery = rememberedSearchValue,
                        rememberedShowSheetState = rememberedShowSheetState,
                        onNextClick = { imagesViewModel.setNextImage() },
                        onPreviousClick = { imagesViewModel.setPreviousImage() },
                        onQueryChange = { value ->
                            rememberedSearchValue = value
                        },
                        onSearch = {
                            imagesViewModel.searchImages(keyword = rememberedSearchValue)
                        },
                        onImageIndexValueChange = {
                            rememberedCurrentImageIndex = it
                        },
                        rememberedCurrentImageIndex = rememberedCurrentImageIndex,
                        onIndexConfirm = {
                            imagesViewModel.updateIndex()
                        },
                        changeShowSheetState = { showState ->
                            rememberedShowSheetState = showState
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        com.airbender.artspace.ui.components.TopAppBar()
        ArtWall()
    }
}


