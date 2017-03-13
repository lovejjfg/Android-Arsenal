package com.lovejjfg.arsenal.utils;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Joe on 2017/3/13.
 * Email lovejjfg@gmail.com
 */

public class TagUtils {

    private static HashMap<String, String> hashMap = new HashMap<>();

    public static void initTags(HashMap<String, String> tags) {
        if (tags == null) {
            return;
        }
        if (tags.size() == hashMap.size()) {
            return;
        }
        hashMap.clear();
        hashMap.putAll(tags);
    }

    public static String getTagValue(String key) {
        if (!hashMap.isEmpty()) {
            return hashMap.get(key);
        }
        return null;
    }

    public static String[] getTagArray() {
        Set<String> strings = hashMap.keySet();
        if (!strings.isEmpty()) {
            return strings.toArray(new String[strings.size()]);
        } else {
            return null;
        }
    }

}
