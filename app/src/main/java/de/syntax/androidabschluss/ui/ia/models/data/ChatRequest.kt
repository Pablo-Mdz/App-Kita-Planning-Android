package de.syntax.androidabschluss.ui.ia.models.data
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatRequest(
    @Json(name = "messages")
    val messages: List<Message>,
    @Json(name = "model")
    val model: String
)