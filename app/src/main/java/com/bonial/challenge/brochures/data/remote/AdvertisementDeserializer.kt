package com.bonial.challenge.brochures.data.remote

import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.model.AdvertisementContent
import com.bonial.challenge.brochures.data.model.BrochureContent
import com.bonial.challenge.brochures.data.model.CarouselBannerItemWrapper
import com.bonial.challenge.brochures.data.model.SuperBannerCarouselContent
import com.bonial.challenge.brochures.data.model.toAdvertisementContentType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

internal class AdvertisementDeserializer : JsonDeserializer<Advertisement> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Advertisement {
        val jsonObject = json.asJsonObject
        val contentType = jsonObject.get("contentType").asString
        val contentJson = jsonObject.get("content")

        val content: AdvertisementContent = when (contentType) {
            "brochure", "brochurePremium" -> context.deserialize(
                contentJson,
                BrochureContent::class.java
            )

            "superBannerCarousel" -> {
                SuperBannerCarouselContent(
                    context.deserialize(
                        contentJson,
                        object : TypeToken<List<CarouselBannerItemWrapper>>() {}.type
                    )
                )
            }
            else -> throw JsonParseException("Unknown content type: $contentType")
        }

        return Advertisement(
            contentType = contentType.toAdvertisementContentType(),
            content = content
        )
    }
}
