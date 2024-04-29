package de.syntax.androidabschluss.ui.ia.models.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Choice(
    @Json(name = "index") val index: Int,
    @Json(name = "message") val message: Message
)