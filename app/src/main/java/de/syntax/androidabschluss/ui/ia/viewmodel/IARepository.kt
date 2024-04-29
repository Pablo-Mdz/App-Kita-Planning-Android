package de.syntax.androidabschluss.ui.ia.viewmodel

import de.syntax.androidabschluss.ui.ia.models.data.ChatRequest
import de.syntax.androidabschluss.ui.ia.models.data.ChatResponse
import de.syntax.androidabschluss.ui.ia.remote.OpAiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IARepository  {
        private val apiClient = OpAiClient.getInstance()
        fun createChatCompletion(chatRequest: ChatRequest, onSuccess: (ChatResponse?) -> Unit,
                                 onError: (String) -> Unit) {
            apiClient.createChatCompletion(chatRequest)
                .enqueue(object : Callback<ChatResponse> {
                    override fun onResponse(
                        call: Call<ChatResponse>,
                        response: Response<ChatResponse>
                    ) {
                        if (response.isSuccessful) { onSuccess(response.body())
                        } else {
                            onError(
                                "API call: returned an error ${response.errorBody()?.string()}"
                            )
                        }
                    }
                    override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                        onError("API call failed: ${t.message}")
                    }
                })
        }
    }