package com.gthm.m3ueditor.m3u.model;

import java.util.Map;

public  class M3UEntry {
    private String name;
    private String url;
    private String groupTitle;
    private Map<String, String> attributes;
    private String firstLine;
    private String secondLine;

    public M3UEntry(String name, String url, String groupTitle, Map<String, String> attributes, String firstLine, String secondLine) {
        this.name = name;
        this.url = url;
        this.groupTitle = groupTitle;
        this.attributes = attributes;
        this.firstLine = firstLine;
        this.secondLine = secondLine;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }

    @Override
    public String toString() {
        return "M3UEntry{" +
                "name='" + name + '\'' +
                ", groupTitle='" + groupTitle + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}