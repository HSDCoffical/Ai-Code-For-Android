package com.example.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = state.baseUrl, onValueChange = { viewModel.updateFields(it, state.apiKey, state.model) }, label = { Text("Base URL") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = state.apiKey, onValueChange = { viewModel.updateFields(state.baseUrl, it, state.model) }, label = { Text("API Key") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = state.model, onValueChange = { viewModel.updateFields(state.baseUrl, state.apiKey, it) }, label = { Text("Model") }, modifier = Modifier.fillMaxWidth())
        Button({ viewModel.saveSettings() }, modifier = Modifier.fillMaxWidth()) { Text("保存设置") }
        Text("仅支持 OpenAI 格式接口", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}