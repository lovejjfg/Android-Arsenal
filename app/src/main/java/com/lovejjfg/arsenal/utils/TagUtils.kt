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

package com.lovejjfg.arsenal.utils

import com.litesuits.orm.db.assit.QueryBuilder
import com.lovejjfg.arsenal.api.mode.SearchInfo
import com.lovejjfg.arsenal.ui.ArsenalListInfoFragment

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Joe on 2017/3/13.
 * Email lovejjfg@gmail.com
 */

object TagUtils {

    private val HASH_MAP = HashMap<String, String?>()

    val tagArray: Array<String>?
        get() {
            val infos = queryArrayList(SearchInfo::class.java)
            if (infos != null && !infos.isEmpty()) {
                for (info in infos) {
                    HASH_MAP[info.key] = info.value
                }
            }
            val strings = HASH_MAP.keys
            return if (!strings.isEmpty()) {
                strings.toTypedArray()
            } else {
                null
            }
        }

    fun getTagValue(key: String): String? {
        return if (!HASH_MAP.isEmpty()) {
            HASH_MAP[key]
        } else null
    }

    fun isSaveTag(size: Int): Boolean {

        return LiteOrmHelper.getInstance().queryCount(
            QueryBuilder(SearchInfo::class.java).whereEquals(
                "type",
                ArsenalListInfoFragment.TYPE_SEARCH_TAG
            )
        ) >= size
    }

    fun deleteSearchTag(): Boolean {

        return LiteOrmHelper.getInstance().delete(
            QueryBuilder(SearchInfo::class.java).whereEquals(
                "type",
                ArsenalListInfoFragment.TYPE_SEARCH
            )
        ) > 0
    }

    fun <T> save(collection: Collection<T>): Int {
        return LiteOrmHelper.getInstance().save(collection)
    }

    fun save(t: Any): Long {
        return LiteOrmHelper.getInstance().save(t)
    }

    fun <T> queryArrayList(t: Class<T>): ArrayList<T>? {
        return LiteOrmHelper.getInstance().query(t)
    }
}
