package com.vanphong.foodnfitbe.utils;

public class YouTubeUtils {
    public static String extractYouTubeVideoId(String url) {
        if (url == null || url.isEmpty()) return null;

        // Regex cho cả youtube.com và youtu.be
        String pattern = "^(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([\\w-]{11})";

        java.util.regex.Pattern compiledPattern = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1); // ID video
        }

        return null;
    }
}
