package com.example.s8126540francoisassessmenttwo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemData(
    val entities: List<Entity>,
    val entityTotal: Int
): Parcelable