package com.taypih.lurker.utils;

public class UrlUtils {
    public static String getFixedUrl(String url) {
        return url == null ? null :
                url.replaceAll("amp;", "");
    }

    public static boolean isGif(String url) {
        return url != null && (url.endsWith(".gif"));
    }

    public static boolean isImage(String url) {
        return url.endsWith(".jpg");
    }

    public static boolean isRedditVideo(String url) {
        return url.startsWith("https://v.redd.it/");
    }
}
