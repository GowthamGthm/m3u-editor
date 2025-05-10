package com.gthm.m3ueditor.m3u.util;

import java.util.Arrays;
import java.util.List;

public class MovieDetector {

    // List of common video formats (lowercase)
    private static final List<String> VIDEO_FORMATS = Arrays.asList(
            "mp4", "avi", "mkv", "mov", "wmv", "flv", "webm", "mpeg", "mpg",
            "m4v", "3gp", "3g2", "f4v", "swf", "vob", "ogv", "ogg", "drc",
            "mts", "m2ts", "ts", "qt", "mxf", "asf", "ifo", "bup", "yuv",
            "rm", "rmvb", "viv", "amv", "nsv", "roq", "svi", "m2v", "m4p",
            "mp2", "mpv"
    );


    /**
     * Extracts the file extension from a URL
     * @param url The URL to analyze
     * @return The file extension in lowercase, or empty string if none found
     */
    private static String getFileExtensionFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "";
        }

        // Remove query parameters and fragments if present
        String cleanUrl = url.split("[?#]")[0];

        // Get everything after the last dot
        int lastDotIndex = cleanUrl.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == cleanUrl.length() - 1) {
            return "";
        }

        return cleanUrl.substring(lastDotIndex + 1).toLowerCase();
    }

    /**
     * Checks if a file extension is a known video format
     * @param url The file extension to check
     * @return true if the extension is a video format, false otherwise
     */
    public static boolean isMovie(String url) {
        String replacedUrl = url.replace("'", "");
        return VIDEO_FORMATS.contains(getFileExtensionFromUrl(replacedUrl));
    }


}