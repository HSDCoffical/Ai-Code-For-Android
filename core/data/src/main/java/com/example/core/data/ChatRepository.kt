package com.example.core.data

import com.example.core.model.ChatRequest
import com.example.core.model.Message
import com.example.core.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first          // 必须导入
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory   // 必须导入
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
        val settings = settingsRepo.settingsFlow.first()   // 现在可解析
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
                val content = extractContentFromJson(data)
                if (content != null) emit(content)
            }
        }
        reader.close()
    }

    private fun extractContentFromJson(json: String): String? {
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