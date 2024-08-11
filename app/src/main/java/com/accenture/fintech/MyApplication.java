package com.accenture.fintech;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Firebase 初期化
        FirebaseApp.initializeApp(this);

        // App Check Play Integrityプロバイダの設定
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());
    }
}