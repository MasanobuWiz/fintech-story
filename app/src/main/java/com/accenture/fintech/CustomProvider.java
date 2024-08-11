package com.accenture.fintech;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.AppCheckProvider;
import com.google.firebase.appcheck.AppCheckProviderFactory;
import com.google.firebase.appcheck.AppCheckToken;

public class CustomProvider implements AppCheckProvider, AppCheckProviderFactory {

    private final String token;
    private final long expiration;

    public CustomProvider() {
        // トークンを設定し、有効期限を1年後に設定
        this.token = "C711DB84-C68B-4A48-B013-216A406FAA50";
        this.expiration = System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000; // 1年間のミリ秒
    }

    @NonNull
    @Override
    public Task<AppCheckToken> getToken() {
        // トークンの有効期限を1分前に調整
        long expMillis = expiration - 60000L;

        // AppCheckTokenオブジェクトを作成
        AppCheckToken appCheckToken = new CustomAppCheckToken(token, expMillis);
        return Tasks.forResult(appCheckToken);
    }

    @NonNull
    @Override
    public AppCheckProvider create(@NonNull FirebaseApp firebaseApp) {
        return this;
    }

    private static class CustomAppCheckToken extends AppCheckToken {
        private final String token;
        private final long expiration;

        CustomAppCheckToken(String token, long expiration) {
            this.token = token;
            this.expiration = expiration;
        }

        @NonNull
        @Override
        public String getToken() {
            return token;
        }

        @Override
        public long getExpireTimeMillis() {
            return expiration;
        }
    }
}