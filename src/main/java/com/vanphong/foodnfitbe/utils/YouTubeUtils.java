package com.vanphong.foodnfitbe.utils;

public class YouTubeUtils {
    public static String extractYouTubeVideoId(String url) {
        if (url == null || url.isEmpty()) return null;

        // Trường hợp dạng đầy đủ
        if (url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2).split("&")[0];
        }

        // Trường hợp dạng ngắn: youtu.be/dQw4w9WgXcQ
        if (url.contains("youtu.be/")) {
            return url.substring(url.lastIndexOf("/") + 1);
        }

        return url; // fallback nếu không match
    }

}
