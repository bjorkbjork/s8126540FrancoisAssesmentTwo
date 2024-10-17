package com.example.s8126540francoisassessmenttwo.network

import com.example.s8126540francoisassessmenttwo.jsonAdapter.EntityAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.s8126540francoisassessmenttwo.network.RestfulApiDevService

class RestfulApiDevRetrofitClient {
    private val BASE_URL = "https://nit3213-api-h2b3-latest.onrender.com/"
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val moshi = Moshi.Builder()
        .add(EntityAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

    val restfulApiDevService: RestfulApiDevService = retrofit.create(RestfulApiDevService::class.java)
}