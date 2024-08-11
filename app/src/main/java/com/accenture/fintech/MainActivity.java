package com.accenture.fintech;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.accenture.fintech.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        // Firebase 初期化
        FirebaseApp.initializeApp(this);
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setCrashlyticsCollectionEnabled(true); // Crashlytics のコレクションを有効にする

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Default Channel for app notifications");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (!notificationManagerCompat.areNotificationsEnabled()) {
            // 通知の許可を求めるUIを表示
            // 通知が許可されていない場合の処理を実装
        }

        // ContentFragmentを表示
        if (savedInstanceState == null) {
            loadFragment(new ContentFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        // フラグメントマネージャーを取得
        FragmentManager fragmentManager = getSupportFragmentManager();
        // トランザクションを開始
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // フラグメントを置き換える
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        // トランザクションをコミット
        fragmentTransaction.commit();
    }
}
