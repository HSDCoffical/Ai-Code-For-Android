package com.example.myaitest;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 显示一个简单的文本
            TextView tv = new TextView(this);
            tv.setText("Hello from Java!\n应用启动成功！");
            tv.setTextSize(24);
            tv.setGravity(android.view.Gravity.CENTER);
            setContentView(tv);
            Log.i(TAG, "onCreate: 启动成功");
        } catch (Throwable e) {
            // 捕获所有异常（包括Error）
            Log.e(TAG, "onCreate: 启动失败", e);
            // 显示Toast
            Toast.makeText(this, "启动失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
            // 显示AlertDialog
            new AlertDialog.Builder(this)
                    .setTitle("启动失败")
                    .setMessage("错误信息: " + e.getMessage() + "\n\n详情请查看Logcat")
                    .setPositiveButton("确定", null)
                    .show();
            // 重新抛出,让系统记录
            throw new RuntimeException(e);
        }
    }
}