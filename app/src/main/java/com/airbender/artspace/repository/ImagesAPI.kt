package com.airbender.artspace.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


class ImagesAPI {
    private var api: Images

    init {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pexels.com")
            .addConverterFactory(GsonConverterFactory.create())
            //.client(okhttpClient)
            .build()
        api = retrofit.create(Images::class.java)
    }

    suspend fun getImages(
        keyword: String = "welcome",
        totalPages: Int = 1,
        perPage: Int = 10,
        orientation: String = "portrait"
    ) = api.searchImage(keyword, totalPages, perPage, orientation)

    interface Images {
        @Headers("Authorization: SFVEGErg8Lp83psGTFJgJicXir6L8QuHw37lyTQdHK3nvfkyF5DAoef9")
        @GET("/v1/search")
        suspend fun searchImage(
            @Query("query") keyword: String,
            @Query("page") totalPages: Int,
            @Query("per_page") perPage: Int,
            @Query("orientation") orientation: String
        ): PexelsResponse
    }
}