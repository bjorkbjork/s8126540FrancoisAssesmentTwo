package com.example.s8126540francoisassessmenttwo.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
data class Entity(
    val key1: String,
    val key2: String,
    val key3: String,
    val key4: Int,
    val key5: String
):Parcelable