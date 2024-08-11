# ベースイメージとしてOpenJDK 11を使用
FROM openjdk:11-jdk-slim

# 必要なツールのインストール
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    git \
    libstdc++6 \
    libpulse0 \
    libbz2-1.0 \
    libx11-6 \
    libglu1-mesa \
    && apt-get clean

# Android SDKの設定
ENV ANDROID_SDK_ROOT="/opt/android-sdk"
ENV PATH="${PATH}:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools"

# Android SDKのインストール
RUN mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools && cd ${ANDROID_SDK_ROOT}/cmdline-tools \
    && wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip -O commandlinetools.zip \
    && unzip commandlinetools.zip && rm commandlinetools.zip \
    && mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools/latest \
    && mv cmdline-tools/* ${ANDROID_SDK_ROOT}/cmdline-tools/latest \
    && mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools/bin \
    && ln -s ${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager ${ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager

# 必要なAndroid SDKパッケージのインストール
RUN yes | sdkmanager --licenses \
    && sdkmanager "platform-tools" "platforms;android-30" "build-tools;30.0.3"

# Gradleのインストール
RUN wget https://services.gradle.org/distributions/gradle-7.2-bin.zip \
    && unzip gradle-7.2-bin.zip -d /opt \
    && ln -s /opt/gradle-7.2/bin/gradle /usr/bin/gradle

# デフォルトの作業ディレクトリを設定
WORKDIR /workspace

# コンテナが終了しないようにするためのコマンド
CMD ["tail", "-f", "/dev/null"]
