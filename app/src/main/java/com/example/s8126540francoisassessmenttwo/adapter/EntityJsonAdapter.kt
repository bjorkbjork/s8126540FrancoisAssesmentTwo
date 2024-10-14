package com.example.s8126540francoisassessmenttwo.adapter

import com.example.s8126540francoisassessmenttwo.data.Entity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader

class EntityJsonAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Entity {
        var stringValues = mutableListOf<String>()
        var intValue: Int? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.peek()) {
                JsonReader.Token.STRING -> {
                    stringValues.add(reader.nextString())
                }
                JsonReader.Token.NUMBER -> {
                    intValue = reader.nextInt()
                }
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        if (stringValues.size == 4 && intValue != null) {
            return Entity(
                key1 = stringValues[0],
                key2 = stringValues[1],
                key3 = stringValues[2],
                key4 = intValue,
                key5 = stringValues[3]
            )
        } else {
            throw JsonDataException("Invalid JSON format: expected 4 strings and 1 integer.")
        }
    }
}

