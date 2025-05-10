package com.gthm.m3ueditor.m3u;

import com.gthm.m3ueditor.m3u.model.M3UEntry;
import com.gthm.m3ueditor.m3u.model.M3uOrganiser;
import com.gthm.m3ueditor.m3u.model.M3uType;
import com.gthm.m3ueditor.m3u.util.MovieDetector;
import com.gthm.m3ueditor.m3u.util.WebSeriesDetector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class M3UProcessor {

    // Pattern to extract group title from EXTINF line
    private static final Pattern GROUP_PATTERN = Pattern.compile("group-title=\"([^\"]*)\"");

    // Represents a single M3U entry

    // Parses an M3U file and returns grouped entries
    public static Map<String, List<M3UEntry>> parseAndGroupM3U(String filePath) throws IOException {
        Map<String, List<M3UEntry>> groupedEntries = new HashMap<>();
        List<M3UEntry> currentGroup = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentUrl = null;
            String currentName = null;
            String currentGroupTitle = null;
            Map<String, String> currentAttributes = null;

            String firstLine = "", secondLine = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#EXTINF:")) {
                    // Parse EXTINF line
                    firstLine = line.trim();
                    currentGroupTitle = extractGroupTitle(line);
                    currentName = extractName(line);
                    currentAttributes = parseAttributes(line);
                } else if (!line.startsWith("#") && !line.trim().isEmpty()) {
                    // This is the URL line
                    currentUrl = line.trim();
                    secondLine = line.trim();

                    if (currentName != null && currentUrl != null) {
                        // Create entry and add to appropriate group
                        M3UEntry entry = new M3UEntry(currentName, currentUrl, currentGroupTitle, currentAttributes, firstLine, secondLine);

                        // Use "Ungrouped" if no group title was found
                        String groupKey = (currentGroupTitle != null && !currentGroupTitle.isEmpty())
                                ? currentGroupTitle : "Ungrouped";

                        groupedEntries.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(entry);

                        // Reset for next entry
                        currentName = null;
                        currentUrl = null;
                        currentGroupTitle = null;
                        currentAttributes = null;
                    }
                }
            }
        }

        return groupedEntries;
    }

    // Extracts group-title from EXTINF line
    private static String extractGroupTitle(String extinfLine) {
        Matcher matcher = GROUP_PATTERN.matcher(extinfLine);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // Extracts the name/title from EXTINF line (after the last comma)
    private static String extractName(String extinfLine) {
        int lastComma = extinfLine.lastIndexOf(',');
        if (lastComma != -1 && lastComma + 1 < extinfLine.length()) {
            return extinfLine.substring(lastComma + 1).trim();
        }
        return "Unknown";
    }

    // Parses all attributes from EXTINF line
    private static Map<String, String> parseAttributes(String extinfLine) {
        Map<String, String> attributes = new HashMap<>();
        // Pattern to match key="value" pairs
        Pattern attrPattern = Pattern.compile("(\\w+)=\"([^\"]*)\"");
        Matcher matcher = attrPattern.matcher(extinfLine);

        while (matcher.find()) {
            attributes.put(matcher.group(1), matcher.group(2));
        }

        return attributes;
    }

    public static List<M3uOrganiser> processFile(String m3uFilePath) {


        try {
            Map<String, List<M3UEntry>> groupedChannels = parseAndGroupM3U(m3uFilePath);


            List<M3uOrganiser> m3uOrganiserList = new ArrayList<>();

            // Print grouped results
            for (Map.Entry<String, List<M3UEntry>> entry : groupedChannels.entrySet()) {
                System.out.println("\nGroup: " + entry.getKey());
//                System.out.println("Number of channels: " + entry.getValue().size());

                M3uOrganiser m3uOrganiser = checkIFLiveTV(entry.getValue());
                m3uOrganiserList.add(m3uOrganiser);

            }
            printM3uOrganiser(m3uOrganiserList);
            return m3uOrganiserList;

        } catch (IOException e) {
            System.err.println("Error processing M3U file: " + e.getMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static void printM3uOrganiser(List<M3uOrganiser> m3uOrganiserList) {

        m3uOrganiserList.forEach(ele -> {
            ele.getM3uList().stream().limit(2)
                    .forEach(ele1 -> System.out.println("Type: " + ele.getType() + " Group: " + ele.getGroupName() + " Name: " + ele1.getName() + " URL: " + ele1.getUrl()));
        });


        System.out.println("=================================================");

    }

    private static M3uOrganiser checkIFLiveTV(List<M3UEntry> values) {

        M3uOrganiser m3uOrganiser = new M3uOrganiser();

        M3UEntry m3UEntry = values.get(0);

        String name = m3UEntry.getName().replace("'", "");
        boolean isWebSeries = WebSeriesDetector.containsWebSeriesPattern(name);

        if (!isWebSeries) {
            if (MovieDetector.isMovie(m3UEntry.getUrl())) {
                m3uOrganiser.setType(M3uType.MOVIES);
            } else {
                m3uOrganiser.setType(M3uType.LIVE_TV);
            }
        } else {
            m3uOrganiser.setType(M3uType.SERIES);
        }

        m3uOrganiser.setGroupName(m3UEntry.getGroupTitle());
        m3uOrganiser.setM3uList(values);

        return m3uOrganiser;

    }


}