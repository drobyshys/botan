package org.vs_07.botan.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseReader extends ResourceFileReader {

    private static final String DEFAULT_RESPONSE_FILE = "botchat-english.response";
    private static final String KEYWORD = "@KEY";
    private static final String COMMA = ",";

    private ResponseReader() {
        // util class
    }

    public static List<String> getByKeys(String message) {
        List<String> responses = new ArrayList<String>();
        try {
            String fileContents = getFileContents(DEFAULT_RESPONSE_FILE);
            String[] lines = toLines(fileContents);
            Integer startIndex = null;
            for (int i = 0; lines.length > i; i++) {
                String line = lines[i];
                if(line.toUpperCase().startsWith(KEYWORD)){
                    List<String> keywords = getKeywords(line);
                    if(foundMatch(message, keywords)){
                        startIndex = i + 1;
                        break;
                    }
                }
            }
            if(startIndex == null) return responses; // return if did not match any keyword on file

            responses.addAll(getResponseLines(startIndex, lines));
        } catch (IOException e) {
            // TODO: Add logger
            e.printStackTrace();
        }
        return responses;
    }

    private static List<String> getResponseLines(Integer startIndex, String[] lines){
        List<String> responseLines = new ArrayList<String>();

        for (int i = startIndex; lines.length > i; i++) {
            String line = lines[i];
            if(StringUtils.normalizeSpace(line).equals("") || line.toUpperCase().startsWith(KEYWORD)){
                break;
            } else {
                responseLines.add(line);
            }
        }

        return responseLines;

    }

    private static boolean foundMatch(String message, List<String> keywords){
        boolean found = false;
        for (String keyword : keywords) {
            if(message.toUpperCase().contains(keyword)) {
                System.out.println("Matched " + keyword);
                found = true;
                break;
            }
        }
        return found;
    }

    private static String[] toLines(String base) {
        return StringUtils.split(base, "\n");
    }

    private static List<String> getKeywords(String line) {
        List<String> keywords = new ArrayList<String>();
        line = cleanKeywordLine(line);
        if (line.contains(COMMA)) {
            String[] rawKeywords = StringUtils.split(line, COMMA);
            for (String rawKeyword : rawKeywords) { // trim keywords then add
                keywords.add(rawKeyword.trim());
            }
        } else {
            keywords.add(line);
        }
        return keywords;
    }

    private static String cleanKeywordLine(String line) {
        line = StringUtils.remove(line, KEYWORD); // Remove the keyword to fetch only the needed strings
        line = StringUtils.remove(line, "\""); // Remove the double quotes
        line = line.trim();
        return line;
    }


}
