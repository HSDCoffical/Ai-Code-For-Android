package com.example.myaitest

import android.app.Application
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // 全局异常捕获
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            val stack = e.stackTrace.joinToString("\n")
            // 显示 Toast，但可能来不及，所以写入日志
            try {
                Toast.makeText(this, "应用崩溃: ${e.message}", Toast.LENGTH_LONG).show()
            } catch (ignored: Exception) {}
            // 打印堆栈到 logcat
            android.util.Log.e("MyApp", "Uncaught exception", e)
            // 重新抛出让系统处理
            Thread.getDefaultUncaughtExceptionHandler()?.uncaughtException(Thread.currentThread(), e)
        }
    }
}