package com.example.s8126540francoisassessmenttwo.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @Json(name = "username")val username: String?,
    @Json(name = "password")val password: String?,
):Parcelable
