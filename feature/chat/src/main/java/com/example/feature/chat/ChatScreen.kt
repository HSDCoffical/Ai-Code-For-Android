package com.example.feature.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel()) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.weight(1f), reverseLayout = true) {
            items(messages.reversed()) { msg ->
                Card(
                    modifier = Modifier.padding(8.dp).fillMaxWidth(if (msg.role == "user") 0.8f else 0.9f),
                    colors = CardDefaults.cardColors(
                        containerColor = if (msg.role == "user") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(msg.content.ifEmpty { if (isLoading) "思考中..." else "空" }, modifier = Modifier.padding(16.dp))
                }
            }
        }
        Row(Modifier.fillMaxWidth().padding(8.dp)) {
            OutlinedTextField(value = inputText, onValueChange = { inputText = it }, modifier = Modifier.weight(1f), enabled = !isLoading)
            Button({ viewModel.sendMessage(inputText); inputText = "" }, enabled = !isLoading) { Text("发送") }
        }
    }
}