package com.example.myaitest

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        try {
            super.onCreate()
        } catch (e: Exception) {
            // 如果 Hilt 初始化失败，捕获并显示
            showError("Application init error: ${e.message}")
        }

        // 全局异常捕获
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            // 尝试在主线程显示 Toast
            Handler(Looper.getMainLooper()).post {
                try {
                    Toast.makeText(
                        this,
                        "崩溃: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                } catch (ignored: Exception) {}
            }
            // 打印堆栈
            android.util.Log.e("MyApp", "Uncaught exception", e)
            // 让系统处理
            Thread.getDefaultUncaughtExceptionHandler()?.uncaughtException(thread, e)
        }
    }

    private fun showError(msg: String) {
        android.util.Log.e("MyApp", msg)
        // 可以在此写入文件
    }
}