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

import com.litesuits.orm.LiteOrm;
import com.lovejjfg.arsenal.BuildConfig;
import com.lovejjfg.arsenal.base.App;

/**
 * Created by Joe on 2017/3/17.
 * Email lovejjfg@gmail.com
 */

public class LiteOrmHelper {

    private static final String DB_NAME = "Android-Arsenal.db";

    private static volatile LiteOrm INSTANCE;

    private LiteOrmHelper() {
    }

    public static LiteOrm getInstance() {
        if (INSTANCE == null) {
            synchronized (LiteOrmHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = LiteOrm.newSingleInstance(App.getInstance(), DB_NAME);
                    INSTANCE.setDebugged(BuildConfig.IS_DEBUG);
                }
            }
        }
        return INSTANCE;
    }
//
//    public static <T> int save(Collection<T> collection) {
//        if (INSTANCE == null) {
//            getInstance();
//        }
//        return INSTANCE.save(collection);
//    }
//
//    public static long save(Object t) {
//        if (INSTANCE == null) {
//            getInstance();
//        }
//        return INSTANCE.save(t);
//    }
//
//    public static <T> ArrayList<T> queryArrayList(Class<T> t) {
//        Log.e("TAG", "save当前保存的数量: " + INSTANCE.queryCount(t));
//        if (INSTANCE == null) {
//            getInstance();
//        }
//        return INSTANCE.query(t);
//    }


}
