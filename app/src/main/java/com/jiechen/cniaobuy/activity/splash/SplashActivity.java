package com.jiechen.cniaobuy.activity.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jiechen.cniaobuy.R;
import com.jiechen.cniaobuy.activity.MainActivity;
import com.jiechen.cniaobuy.activity.guide.GuideActivity;

/**
 * 闪屏界面
 */
public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                boolean isFirst = sp.getBoolean("isFirst", true);
                Intent intent = new Intent();

                if (isFirst) {
                    sp.edit().putBoolean("isFirst", false).apply();
                    //如果用户是第一次安装应用，进入引导页面
                    intent.setClass(SplashActivity.this, GuideActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }

                startActivity(intent);
                // 设置界面之间的切换动画
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                finish();
            }
        }, 2000);
    }
}
