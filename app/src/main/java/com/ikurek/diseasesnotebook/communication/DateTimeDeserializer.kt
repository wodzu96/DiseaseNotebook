package com.ikurek.diseasesnotebook.communication

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.joda.time.DateTime
import java.lang.reflect.Type

class DateTimeDeserializer : JsonDeserializer<DateTime> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DateTime {
        var dateTime: DateTime = DateTime.now()

        try {
            if (json.isJsonPrimitive) {
                json.asJsonPrimitive?.let {
                    if (it.isNumber)
                        dateTime = DateTime(it.asLong)
                    else if (it.isString)
                        dateTime = DateTime(it.asString)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            dateTime = DateTime.now()
        }

        return dateTime
    }
}
