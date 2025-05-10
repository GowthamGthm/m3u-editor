package com.gthm.m3ueditor.m3u.util;

import java.util.regex.*;

public class WebSeriesDetector {

    // Regular expression to match web series episode patterns
    private static final Pattern WEB_SERIES_PATTERN = Pattern.compile(
            "(?i)(?<![a-z0-9])(?:S(\\d{1,4})(?:[\\s-]*E(\\d{1,4}))|Season[\\s]+(\\d{1,4})(?:[\\s]+Episode[\\s]+(\\d{1,4}))?)(?![a-z0-9])"
    );

    /**
     * Checks if the input string contains a web series episode pattern
     * @param input The string to check
     * @return true if a web series pattern is found, false otherwise
     */
    public static boolean containsWebSeriesPattern(String input) {
        return WEB_SERIES_PATTERN.matcher(input).find();
    }

    /**
     * Extracts season and episode numbers if found in the input
     * @param input The string to analyze
     * @return int array with [season, episode] or null if no match found
     */
    public static int[] extractSeasonAndEpisode(String input) {
        Matcher matcher = WEB_SERIES_PATTERN.matcher(input);
        if (matcher.find()) {
            // Check which pattern matched (SXXEYY or Season X Episode Y)
            int season, episode;
            if (matcher.group(1) != null) {
                // SXXEYY format
                season = Integer.parseInt(matcher.group(1));
                episode = Integer.parseInt(matcher.group(2));
            } else {
                // Season X Episode Y format
                season = Integer.parseInt(matcher.group(3));
                episode = Integer.parseInt(matcher.group(4));
            }
            return new int[]{season, episode};
        }
        return null;
    }

    /**
     * Gets the full matched episode pattern from the input string
     * @param input The string to search
     * @return The matched pattern or null if not found
     */
    public static String getMatchedPattern(String input) {
        Matcher matcher = WEB_SERIES_PATTERN.matcher(input);
        return matcher.find() ? matcher.group() : null;
    }

//    public static void main(String[] args) {
//        String testString = "Jamnapaar (2024) S01E10";
//
//        // Check if contains web series pattern
//        boolean containsPattern = containsWebSeriesPattern(testString);
//        System.out.println("Contains web series pattern: " + containsPattern);
//
//        // Extract season and episode numbers
//        int[] seasonEpisode = extractSeasonAndEpisode(testString);
//        if (seasonEpisode != null) {
//            System.out.println("Season: " + seasonEpisode[0]);
//            System.out.println("Episode: " + seasonEpisode[1]);
//        }
//
//        // Get the full matched pattern
//        String matchedPattern = getMatchedPattern(testString);
//        System.out.println("Matched pattern: " + matchedPattern);
//    }


}