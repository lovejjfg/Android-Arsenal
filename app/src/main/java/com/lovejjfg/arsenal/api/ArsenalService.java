package com.lovejjfg.arsenal.api;

import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
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
