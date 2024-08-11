package com.accenture.fintech;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<String> tokens = split(getMessage()); // 修正後
        String result = join(tokens); // 修正後
    }

    private static String getMessage() {
        return "Hello, World!";
    }

    private static List<String> split(String message) {
        List<String> tokens = new LinkedList<>();
        Collections.addAll(tokens, message.split(" "));
        return tokens;
    }

    private static String join(List<String> tokens) {
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(token).append(" ");
        }
        return sb.toString().trim();
    }
}
