package com.example.myaitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
        setContent {
            AppContent()
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
                            icon = { Text("") },  // 添加图标占位
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