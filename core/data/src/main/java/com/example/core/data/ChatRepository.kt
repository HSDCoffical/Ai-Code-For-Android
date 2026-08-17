package com.example.core.data

import com.example.core.model.ChatRequest
import com.example.core.model.Message
import com.example.core.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val settingsRepo: SettingsRepository
) {
    suspend fun getStreamingResponse(
        messages: List<Message>
    ): Flow<String> = flow {
        val settings = settingsRepo.settingsFlow.first() // 需导入 kotlinx.coroutines.flow.first
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(settings.baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)

        val request = ChatRequest(settings.model, messages)
        val response = api.chatStream(request)
        val reader = BufferedReader(InputStreamReader(response.byteStream()))

        var line: String?
        while (reader.readLine().also { line = it } != null) {
            val raw = line ?: continue
            if (raw.startsWith("data: ")) {
                val data = raw.removePrefix("data: ")
                if (data == "[DONE]") break
                // 简易解析：提取 content
                val content = extractContentFromJson(data)
                if (content != null) emit(content)
            }
        }
        reader.close()
    }

    private fun extractContentFromJson(json: String): String? {
        // 直接截取 "content":"xxx" 避免引入额外解析库依赖，也可用 Moshi
        val key = "\"content\":\""
        val start = json.indexOf(key)
        if (start == -1) return null
        val end = json.indexOf("\"", start + key.length)
        if (end == -1) return null
        return json.substring(start + key.length, end)
            .replace("\\n", "\n")
            .replace("\\\"", "\"")
    }
}