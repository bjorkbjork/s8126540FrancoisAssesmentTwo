package com.example.s8126540francoisassessmenttwo.adapter

import com.example.s8126540francoisassessmenttwo.data.Entity
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi

class ItemDataJsonAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): ItemData {
        val entities = mutableListOf<Entity>()
        var entityTotal: Int? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "entities" -> {
                    reader.beginArray()
                    while (reader.hasNext()) {
                        val entityAdapter: JsonAdapter<Entity> = Moshi.Builder()
                            .add(EntityJsonAdapter())
                            .build()
                            .adapter(Entity::class.java)
                        val entity = entityAdapter.fromJson(reader)
                        if (entity != null) {
                            entities.add(entity)
                        }
                    }
                    reader.endArray()
                }
                "entityTotal" -> {
                    entityTotal = reader.nextInt()
                }
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        if (entityTotal != null) {
            return ItemData(entities = entities, entityTotal = entityTotal)
        } else {
            throw JsonDataException("Invalid JSON format: expected entityTotal.")
        }
    }
}