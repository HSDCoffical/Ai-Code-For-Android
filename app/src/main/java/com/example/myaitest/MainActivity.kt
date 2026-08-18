package com.example.myaitest

import android.os.Bundle
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
        setContent {
            // 直接调用 Composable，不包裹 try-catch
            SafeAppContent()
        }
    }
}

@Composable
fun SafeAppContent() {
    try {
        // 尝试加载正常界面
        AppContent()
    } catch (e: Exception) {
        // 捕获异常显示错误（Composable 内部允许 try-catch）
        android.util.Log.e("MainActivity", "Composable error", e)
        ErrorScreen("启动错误: ${e.message}")
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
    // 简化版测试：显示“应用启动成功！”（如果这个能显示，说明环境正常）
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("应用启动成功！", fontSize = 24.sp)
    }
}