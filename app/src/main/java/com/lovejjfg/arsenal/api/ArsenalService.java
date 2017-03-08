package com.lovejjfg.arsenal.api;

import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Joe on 2017/3/7.
 * Email lovejjfg@gmail.com
 */

public interface ArsenalService {

    @GET(".")
    Observable<List<ArsenalListInfo>> getArsenalListInfo();

    @GET(".")
    Observable<List<ArsenalListInfo>> loadMoreArsenalListInfo(@Query("page") int page);

    @GET("user/{username}")
    Observable<ArsenalUserInfo> getArsenalUserInfo(@Path("username") String user);

    @GET("details/1/{detail}")
    Observable<ArsenalDetailInfo> getArsenalDetailInfo(@Path("detail") String user);
}
