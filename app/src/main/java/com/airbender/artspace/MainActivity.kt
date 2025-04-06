package com.airbender.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.airbender.artspace.presentor.ImagesViewModel
import com.airbender.artspace.presentor.SearchBarViewModel
import com.airbender.artspace.repository.Photo
import com.airbender.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                val imagesViewModel by viewModels<ImagesViewModel>()
                val searchBarViewModel by viewModels<SearchBarViewModel>()
                val rememberedNextImage by remember { imagesViewModel.nextImage }
                var rememberedSearchValue by remember { searchBarViewModel.query }
                val rememberedTotalImages by remember { imagesViewModel.totalImages }
                val rememberedCurrentImage by remember { imagesViewModel.currentIndex }
                var rememberedCurrentImageIndex by remember { imagesViewModel.currentIndexTextFieldValue }
                Scaffold(
                    topBar = {
                        TopAppBar()
                    },
                ) { innerPadding ->
                    ArtWall(
                        innerPadding = innerPadding,
                        photo = rememberedNextImage,
                        totalImages = rememberedTotalImages,
                        currentImage = rememberedCurrentImage ,
                        searchQuery = rememberedSearchValue,
                        onNextClick = { imagesViewModel.setNextImage() },
                        onPreviousClick = { imagesViewModel.setPreviousImage() },
                        onQueryChange = { value ->
                            rememberedSearchValue = value//.trim { it == ' ' }
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
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ArtWall(
    innerPadding: PaddingValues = PaddingValues(),
    photo: Photo = Photo(),
    searchQuery: String = "",
    currentImage: Int = 0,
    totalImages: Int = 0,
    rememberedCurrentImageIndex: String = "",
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit = {},
    onPreviousClick: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onImageIndexValueChange: (String) -> Unit = {},
    onIndexConfirm: () -> Unit = {}
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
            Image(photo, modifier)
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
}

@Composable
fun Image(
    photo: Photo,
    modifier: Modifier = Modifier,
) {
    var isLoading by remember { mutableStateOf(true) }
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 4.dp)
            .size(width = 400.dp, height = 450.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp,
            bottomStart = 16.dp
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.sizedImageUrl.large2x)
                .crossfade(true)
                .scale(Scale.FILL)
                .listener(
                    onStart = { isLoading = true },
                    onSuccess = { _, _ -> isLoading = false },
                    onError = { _, _ -> isLoading = false }
                )
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .fillMaxWidth()
                .size(width = 400.dp, height = 450.dp)
                .padding(12.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(32.dp)
                    .fillMaxSize()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
fun ArtistDetails(photo: Photo) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp,
            bottomStart = 16.dp
        )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = photo.photographer,
                modifier = Modifier.padding(12.dp),
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            Text(
                text = photo.alt,
                modifier = Modifier.padding(12.dp),
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }
    }
}


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = SearchBarColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            dividerColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        shadowElevation = 20.dp,
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = false,
                onExpandedChange = {},
                placeholder = { Text("Search a word") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            )
        },
        expanded = false,
        onExpandedChange = {},
        content = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Search Image",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        TopAppBar()
        ArtWall()
    }
}


