package com.accenture.fintech;

public class Comic {

    private String title;
    private String coverImageUrl;

    public Comic() {
        // Firebase Realtime Database で必要なデフォルトコンストラクタ
    }

    public Comic(String title, String coverImageUrl) {
        this.title = title;
        this.coverImageUrl = coverImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}