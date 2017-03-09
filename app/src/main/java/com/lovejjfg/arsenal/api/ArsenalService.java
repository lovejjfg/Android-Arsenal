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

    @GET("user/{username}")
    Observable<ArsenalUserInfo> getArsenalUserInfo(@Path("username") String user);

    @GET("details/1/{detail}")
    Observable<ArsenalDetailInfo> getArsenalDetailInfo(@Path("detail") String user);

    @GET()
    Observable<ArsenalListInfo> search(@Url() String path);


}
