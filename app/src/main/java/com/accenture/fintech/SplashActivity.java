package com.accenture.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final int SPLASH_DISPLAY_LENGTH = 1000; // 1秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        Log.d(TAG, "onCreate: SplashScreen installed");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: SplashActivity started");

        // FirebaseAppの初期化
        FirebaseApp.initializeApp(/*context=*/ this);

        // タイトルバーを非表示にする
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 1秒後にログイン状態をチェックして適切なアクティビティに遷移
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    Log.d(TAG, "run: User is logged in, navigating to MainActivity");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    Log.d(TAG, "run: User is not logged in, navigating to login");
                    startActivity(new Intent(SplashActivity.this, login.class));
                }
                finish();
                Log.d(TAG, "run: SplashActivity finished");
            }
        }, SPLASH_DISPLAY_LENGTH); // 1000ミリ秒 (1秒)
    }
}