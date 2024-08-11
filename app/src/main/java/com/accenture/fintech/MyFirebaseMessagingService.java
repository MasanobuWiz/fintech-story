package com.accenture.fintech;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // メッセージにデータペイロードが含まれているか確認
        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // メッセージに通知ペイロードが含まれているか確認
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // FCM登録トークンをアプリサーバーに送信
    }
}