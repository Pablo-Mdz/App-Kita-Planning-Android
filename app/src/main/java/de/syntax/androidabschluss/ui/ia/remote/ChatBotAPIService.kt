package de.syntax.androidabschluss.ui.ia.remote


import de.syntax.androidabschluss.ui.ia.models.data.ChatRequest
import de.syntax.androidabschluss.ui.ia.models.data.ChatResponse

import de.syntax.androidabschluss.ui.ia.utils.OPENAI_API_KEY_TEAM_GOOGLE_MAPS
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAiApiService {

    @POST("chat/completions")
    fun createChatCompletion(
        @Body chatRequest: ChatRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authorization: String = "Bearer $OPENAI_API_KEY_TEAM_GOOGLE_MAPS"
    ): Call<ChatResponse>
}