package com.example.s8126540francoisassessmenttwo.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Entity(
    val courseCode: String,
    val courseName: String,
    val instructor: String,
    val credits: Int,
    val description: String
):Parcelable