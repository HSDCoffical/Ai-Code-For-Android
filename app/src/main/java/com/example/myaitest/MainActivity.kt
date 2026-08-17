package com.example.myaitest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                SafeAppContent()
            }
        } catch (e: Exception) {
            // 如果 setContent 本身出错，显示简单界面
            android.util.Log.e("MainActivity", "setContent error", e)
            Toast.makeText(this, "启动失败: ${e.message}", Toast.LENGTH_LONG).show()
            // 显示一个简单的错误文本
            setContent {
                ErrorScreen("启动失败: ${e.message}")
            }
        }
    }
}

@Composable
fun SafeAppContent() {
    try {
        // 原始 AppContent 可以放在这里，但为了防崩溃，我们逐个调用
        AppContent()
    } catch (e: Exception) {
        android.util.Log.e("MainActivity", "Composable error", e)
        ErrorScreen("Composable 错误: ${e.message}")
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, fontSize = 18.sp)
    }
}

@Composable
fun AppContent() {
    // 这里是原来的内容，但为了安全性，我们先用一个简化版本测试
    // 如果您之前的内容没问题，取消注释下面的代码，注释掉简化版本
    /*
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
    */
    // 简化版测试（只显示文字，确保能启动）
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("应用启动成功！", fontSize = 24.sp)
    }
}