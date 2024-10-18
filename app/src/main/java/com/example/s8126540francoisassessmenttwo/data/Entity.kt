package com.example.s8126540francoisassessmenttwo.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Entity(
    val stringKeyOne: String,
    val stringKeyTwo: String,
    val stringKeyThree: String,
    val stringKeyFour: String,
    val stringKeyFive: String,
    val intKey: Int,
    val intTitle: String,
):Parcelable
