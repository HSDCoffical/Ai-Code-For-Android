package com.example.core.model

import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @Streaming
    @POST("v1/chat/completions")
    suspend fun chatStream(@Body request: ChatRequest): ResponseBody
}