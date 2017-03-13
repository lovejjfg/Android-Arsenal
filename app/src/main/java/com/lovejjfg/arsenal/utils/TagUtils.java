/*
 *  Copyright (c) 2017.  Joe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.lovejjfg.arsenal.utils;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Joe on 2017/3/13.
 * Email lovejjfg@gmail.com
 */

public class TagUtils {

    private static final HashMap<String, String> hashMap = new HashMap<>();

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
