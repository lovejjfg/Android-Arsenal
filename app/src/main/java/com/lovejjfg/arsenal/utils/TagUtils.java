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

import com.litesuits.orm.db.assit.QueryBuilder;
import com.lovejjfg.arsenal.api.mode.SearchInfo;
import com.lovejjfg.arsenal.ui.ArsenalListInfoFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Joe on 2017/3/13.
 * Email lovejjfg@gmail.com
 */

public class TagUtils {

    private static final HashMap<String, String> HASH_MAP = new HashMap<>();

    public static String getTagValue(String key) {
        if (!HASH_MAP.isEmpty()) {
            return HASH_MAP.get(key);
        }
        return null;
    }

    public static String[] getTagArray() {
        ArrayList<SearchInfo> infos = queryArrayList(SearchInfo.class);
        if (infos != null && !infos.isEmpty()) {
            for (SearchInfo info : infos) {
                HASH_MAP.put(info.getKey(), info.getValue());
            }
        }
        Set<String> strings = HASH_MAP.keySet();
        if (!strings.isEmpty()) {
            return strings.toArray(new String[strings.size()]);
        } else {
            return null;
        }
    }


    public static boolean isSaveTag(int size) {
        //noinspection unchecked
        return LiteOrmHelper.getInstance().queryCount(new QueryBuilder(SearchInfo.class).whereEquals("type", ArsenalListInfoFragment.TYPE_SEARCH_TAG)) >= size;
    }

    public static boolean deleteSearchTag() {
        //noinspection unchecked
        return LiteOrmHelper.getInstance().delete(new QueryBuilder(SearchInfo.class).whereEquals("type", ArsenalListInfoFragment.TYPE_SEARCH)) > 0;
    }

    public static <T> int save(Collection<T> collection) {
        return LiteOrmHelper.getInstance().save(collection);
    }

    public static long save(Object t) {
        return LiteOrmHelper.getInstance().save(t);
    }

    public static <T> ArrayList<T> queryArrayList(Class<T> t) {
        return LiteOrmHelper.getInstance().query(t);
    }


}
