package com.airbender.artspace.repository

import com.google.gson.annotations.SerializedName

data class PexelsResponse(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("per_page") val perPage: Int = 0,
    @SerializedName("photos") val photos: MutableList<Photo> = mutableListOf(Photo()),
    @SerializedName("total_results") val totalResults: Int = 0,
    @SerializedName("next_page") val nextPage: String = ""
)
data class Photo(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("width") val width: Int = 0,
    @SerializedName("height") val height: Int= 0,
    @SerializedName("url") val url: String = "",
    @SerializedName("photographer") val photographer: String= "",
    @SerializedName("photographer_url")val photographerUrl: String = "",
    @SerializedName("photographer_id")val photographerId : String= "",
    @SerializedName("avg_color") val avgColor: String = "",
    @SerializedName("src")val sizedImageUrl : PhotoSrc = PhotoSrc(),
    @SerializedName("liked") val isLiked: Boolean = false,
    @SerializedName("alt") val alt: String = ""
)

data class PhotoSrc(
    @SerializedName("original") val original: String = "",
    @SerializedName("large2x") val large2x: String = "",
    @SerializedName("large") val large: String = "",
    @SerializedName("medium") val medium: String = "",
    @SerializedName("small") val small: String = "",
    @SerializedName("portrait") val portrait: String = "",
    @SerializedName("landscape") val landscape: String = "",
    @SerializedName("tiny") val tiny: String = ""
)

