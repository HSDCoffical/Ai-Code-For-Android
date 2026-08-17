package com.example.myaitest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feature.chat.ChatScreen
import com.example.feature.settings.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                AppContent()
            }
        } catch (e: Exception) {
            // 捕获 setContent 异常
            Toast.makeText(this, "启动失败: ${e.message}", Toast.LENGTH_LONG).show()
            android.util.Log.e("MainActivity", "onCreate error", e)
            // 显示错误界面（可选）
            setContent {
                Text("启动失败: ${e.message}")
            }
        }
    }
}

@Composable
fun AppContent() {
    MaterialTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: "chat"

        Scaffold(
            bottomBar = {
                NavigationBar {
                    listOf("chat" to "聊天", "settings" to "设置").forEach { (route, label) ->
                        NavigationBarItem(
                            selected = currentRoute == route,
                            onClick = { navController.navigate(route) },
                            icon = { Text("") },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "chat",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("chat") { ChatScreen() }
                composable("settings") { SettingsScreen() }
            }
        }
    }
}