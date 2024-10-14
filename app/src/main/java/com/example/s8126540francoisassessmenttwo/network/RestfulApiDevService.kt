package com.example.s8126540francoisassessmenttwo.network

import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.User
import com.example.s8126540francoisassessmenttwo.data.Keypass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST

interface RestfulApiDevService {

    @GET("dashboard/{keypass}")
    suspend fun getAllObjects(@Path("keypass") keypass:String): List<ItemData>

    @POST("footscray/auth")
    suspend fun addObject( @Body data: User): Keypass
}
