package com.example.s8126540francoisassessmenttwo.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Keypass(
    @Json(name = "keypass")val keypass: String?
):Parcelable
