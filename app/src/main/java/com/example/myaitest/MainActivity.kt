package com.example.myaitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val nav = rememberNavController()
                Scaffold(bottomBar = {
                    NavigationBar {
                        listOf("chat" to "聊天", "settings" to "设置").forEach { (route, label) ->
                            NavigationBarItem(selected = false, onClick = { nav.navigate(route) }, label = { Text(label) })
                        }
                    }
                }) { innerPadding ->
                    NavHost(nav, "chat", Modifier.padding(innerPadding)) {
                        composable("chat") { ChatScreen() }
                        composable("settings") { SettingsScreen() }
                    }
                }
            }
        }
    }
}