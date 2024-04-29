package de.syntax.androidabschluss.ui.ia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntax.androidabschluss.ui.ia.models.data.ChatRequest
import de.syntax.androidabschluss.ui.ia.models.data.Message

class IaViewModel (application: Application) : AndroidViewModel(application) {
        private val chatRepository = IARepository()
        private val _chatResponse = MutableLiveData<String?>()

    val chatResponse: LiveData<String?>
            get() = _chatResponse
        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage
        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading: LiveData<Boolean>
            get() = _isLoading
        fun createChatCompletion(messages: List<Message>, model: String) {
            _isLoading.value = true
            val request = ChatRequest(messages , model)
            chatRepository.createChatCompletion(request, { response ->
                _isLoading.value = false
                _chatResponse.value = response?.choices?.firstOrNull()?.message?.content
            }, { error ->
                _isLoading.value = false
                _errorMessage.value = error
            })
        }
    }