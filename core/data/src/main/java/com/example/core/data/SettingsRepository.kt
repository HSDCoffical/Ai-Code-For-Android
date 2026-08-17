package com.example.core.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

data class Settings(val baseUrl: String, val apiKey: String, val model: String)

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context   // 添加 @ApplicationContext
) {
    companion object {
        val BASE_URL = stringPreferencesKey("base_url")
        val API_KEY = stringPreferencesKey("api_key")
        val MODEL = stringPreferencesKey("model")
    }

    val settingsFlow: Flow<Settings> = context.dataStore.data.map { prefs ->
        Settings(
            baseUrl = prefs[BASE_URL] ?: "https://api.openai.com/v1",
            apiKey = prefs[API_KEY] ?: "",
            model = prefs[MODEL] ?: "gpt-3.5-turbo"
        )
    }

    suspend fun saveSettings(baseUrl: String, apiKey: String, model: String) {
        context.dataStore.edit { prefs ->
            prefs[BASE_URL] = baseUrl
            prefs[API_KEY] = apiKey
            prefs[MODEL] = model
        }
    }
}