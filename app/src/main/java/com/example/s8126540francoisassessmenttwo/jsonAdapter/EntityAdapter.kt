package com.example.s8126540francoisassessmenttwo.jsonAdapter

import com.example.s8126540francoisassessmenttwo.data.Entity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.io.IOException
import kotlin.time.Duration.Companion.milliseconds


// Custom EntityAdapter for dynamic mapping, allowing other students to use the app.
class EntityAdapter : JsonAdapter<Entity>() {

    @Throws(IOException::class)
    @FromJson
    override fun fromJson(reader: JsonReader): Entity? {

        var stringKeyOne = ""
        var stringKeyTwo = ""
        var stringKeyThree = ""
        var stringKeyFour = ""
        var stringKeyFive = ""
        var intKey = 0
        var intTitle = ""


        reader.beginObject()
        while (reader.hasNext()) {
            var key = reader.nextName()
            when (reader.peek()) {
                JsonReader.Token.NUMBER -> {
                    intKey = reader.nextInt()

                    // detect uppercase letters in key, then split into two words
                    key = key.replace(Regex("(?<!^)([A-Z])"), " $1")

                    // make first letter uppercase always
                    intTitle = key.replaceFirstChar{ it.uppercase() }
                }
                JsonReader.Token.STRING -> {
                    val value = reader.nextString()
                    when {
                        stringKeyOne.isEmpty() -> stringKeyOne = value
                        stringKeyTwo.isEmpty() -> stringKeyTwo = value
                        stringKeyThree.isEmpty() -> stringKeyThree = value
                        stringKeyFour.isEmpty() -> stringKeyFour = value
                        intKey == 0 -> stringKeyFive = value
                        stringKeyFive.isEmpty() -> stringKeyFive = value
                    }
                }
                else -> reader.skipValue()  // Skip unexpected tokens

            }
        }
        reader.endObject()

        return Entity(stringKeyOne, stringKeyTwo, stringKeyThree, stringKeyFour, stringKeyFive, intKey, intTitle)
    }

    @Throws(IOException::class)
    @ToJson
    override fun toJson(writer: JsonWriter, value: Entity?) {
        writer.beginObject()
        if (value != null) {
            writer.name("stringKeyOne").value(value.stringKeyOne)
            writer.name("stringKeyTwo").value(value.stringKeyTwo)
            writer.name("stringKeyThree").value(value.stringKeyThree)
            writer.name("stringKeyFour").value(value.stringKeyFour)
            writer.name("stringKeyFive").value(value.stringKeyFive)
            writer.name(value.intTitle).value(value.intKey)
        }
        writer.endObject()
    }
}