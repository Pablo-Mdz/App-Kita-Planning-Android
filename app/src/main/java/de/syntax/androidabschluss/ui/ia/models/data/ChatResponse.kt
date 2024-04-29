package de.syntax.androidabschluss.ui.ia.models.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ChatResponse(
    @Json(name = "choices")
    val choices: List<Choice>
)