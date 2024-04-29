package de.syntax.androidabschluss.ui.ia.models.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "content") val content: String,
    @Json(name = "role") val role: String
)