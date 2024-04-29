package de.syntax.androidabschluss.ui.ia.remote

import de.syntax.androidabschluss.ui.ia.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object OpAiClient {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    // Create a Retrofit object with the OpenAiApiService interface
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    @Volatile
    private var INSTANCE : OpenAiApiService? = null
    fun getInstance() : OpenAiApiService {
        synchronized(this){
            return INSTANCE ?: retrofit.create(OpenAiApiService::class.java).also {
                INSTANCE = it
            }
        }
    }
}