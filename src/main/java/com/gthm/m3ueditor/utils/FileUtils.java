package com.gthm.m3ueditor.utils;

import java.nio.file.Paths;

public class FileUtils {


    public static String getDownloadPath() {
        String userHome = System.getProperty("user.home");
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            // Windows: Typically "C:\Users\<user>\Downloads"
            return Paths.get(userHome, "Downloads").toString();
        } else if (osName.contains("mac") || osName.contains("darwin")) {
            // macOS: Typically "/Users/<user>/Downloads"
            return Paths.get(userHome, "Downloads").toString();
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            // Linux: Typically "/home/<user>/Downloads"
            return Paths.get(userHome, "Downloads").toString();
        } else {
            // Fallback: Use the user's home directory if OS is unknown
            return userHome;
        }
    }

}
