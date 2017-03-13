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

package com.lovejjfg.arsenal.api;

import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Joe on 2017/3/7.
 * Email lovejjfg@gmail.com
 */

public interface ArsenalService {

    @GET(".")
    Observable<ArsenalListInfo> getArsenalListInfo();

    @GET()
    Observable<ArsenalListInfo> loadMoreArsenalListInfo(@Url() String path);

    @GET()
    Observable<ArsenalUserInfo> getArsenalUserInfo(@Url() String user);

    @GET()
    Observable<ArsenalDetailInfo> getArsenalDetailInfo(@Url String detailUrl);

    @GET()
    Observable<ArsenalListInfo> search(@Url() String path);


}
