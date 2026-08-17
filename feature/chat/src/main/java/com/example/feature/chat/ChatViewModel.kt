package com.example.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ChatRepository
import com.example.core.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var streamJob: Job? = null

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        // 1. 添加用户消息
        val userMsg = Message("user", text)
        _messages.value = _messages.value + userMsg

        // 2. 临时占位助手消息
        val assistantPlaceholder = Message("assistant", "")
        _messages.value = _messages.value + assistantPlaceholder
        _isLoading.value = true

        streamJob?.cancel()
        streamJob = viewModelScope.launch {
            val history = _messages.value.dropLast(1) // 去掉占位
            try {
                var fullContent = ""
                repo.getStreamingResponse(history).collect { chunk ->
                    fullContent += chunk
                    // 更新最后一条消息
                    val lastIndex = _messages.value.size - 1
                    val updatedList = _messages.value.toMutableList()
                    updatedList[lastIndex] = Message("assistant", fullContent)
                    _messages.value = updatedList
                }
            } catch (e: Exception) {
                // 网络错误不崩，显示在消息里
                val lastIndex = _messages.value.size - 1
                val updatedList = _messages.value.toMutableList()
                updatedList[lastIndex] = Message("assistant", "❌ 请求失败: ${e.message}")
                _messages.value = updatedList
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessages() { _messages.value = emptyList() }
    override fun onCleared() { streamJob?.cancel() }
}