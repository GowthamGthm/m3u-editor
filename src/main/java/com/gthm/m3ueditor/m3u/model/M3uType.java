package com.gthm.m3ueditor.m3u.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum M3uType {
    LIVE_TV("Live TV"),
    MOVIES("Movies"),
    SERIES("Series");

    private final String displayName;
}