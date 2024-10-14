package com.example.s8126540francoisassessmenttwo.data

import com.example.s8126540francoisassessmenttwo.network.RestfulApiDevRetrofitClient
import com.example.s8126540francoisassessmenttwo.network.RestfulApiDevService
import javax.inject.Inject

class RestfulApiDevRepositoryClass @Inject constructor(private val restfulDevApiService: RestfulApiDevService) {

    suspend fun getAllObjectsData(item: String) = restfulDevApiService.getAllObjects(keypass = item)

    suspend fun addUser(item: User) = restfulDevApiService.addObject(data = item)
}