package com.example.core.model

sealed class ChatRole { object User : ChatRole(); object Assistant : ChatRole() }
data class Message(val role: String, val content: String) // "user" or "assistant"
data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    val stream: Boolean = true
)