package com.gnoemes.shikimoriapp.utils;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class SmotretAnimeUtils {

    public static long getIdFromEmbeddedPlayer(String url) {
        String patternScheme = "(\\d+)";
        Pattern pattern = Pattern.compile(patternScheme);
        if (pattern.matcher(url).find()) {
            return Long.parseLong(url.substring(url.lastIndexOf("/")).replaceFirst("/", ""));
        }

        throw new NoSuchElementException();
    }
}
