package com.example.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: SettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init { viewModelScope.launch {
        val s = repo.settingsFlow.first()
        _uiState.value = SettingsUiState(s.baseUrl, s.apiKey, s.model)
    }}

    fun updateFields(baseUrl: String, apiKey: String, model: String) {
        _uiState.value = _uiState.value.copy(baseUrl = baseUrl, apiKey = apiKey, model = model)
    }

    fun saveSettings() { viewModelScope.launch {
        val state = _uiState.value
        repo.saveSettings(state.baseUrl, state.apiKey, state.model)
    }}
}
data class SettingsUiState(val baseUrl: String = "", val apiKey: String = "", val model: String = "")