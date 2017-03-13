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

package com.lovejjfg.arsenal.api.mode;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Joe on 2017/3/7.
 * Email lovejjfg@gmail.com
 */

public class ArsenalUserInfo implements Parcelable {
    private final String userInfoUrl;
    private String nickname;

    public ArsenalUserInfo(ArrayList<ArsenalListInfo.ListInfo> contributions,
                           String email, String followers,
                           String followersUrl, String following,
                           String followingUrl, String homepage,
                           String location, ArrayList<ArsenalListInfo.ListInfo> ownProjects,
                           String portraitUrl, String publicRepo,
                           String publicRepoUrl, String site,
                           String userInfoUrl, String userName) {
        this.contributions = contributions;
        this.email = email;
        this.followers = followers;
        this.followersUrl = followersUrl;
        this.following = following;
        this.followingUrl = followingUrl;
        this.homepage = homepage;
        this.location = location;
        this.ownProjects = ownProjects;
        this.portraitUrl = portraitUrl;
        this.publicRepo = publicRepo;
        this.publicRepoUrl = publicRepoUrl;
        this.site = site;
        this.userInfoUrl = userInfoUrl;
        this.userName = userName;
    }

    private final String userName;
    private final String portraitUrl;
    private final String email;
    private final String location;
    private final String site;
    private final String homepage;
    private final String followers;
    private final String followersUrl;
    private final String following;
    private final String followingUrl;
    private final String publicRepo;
    private final String publicRepoUrl;

    private final ArrayList<ArsenalListInfo.ListInfo> ownProjects;
    private final ArrayList<ArsenalListInfo.ListInfo> contributions;

    public ArrayList<ArsenalListInfo.ListInfo> getContributions() {
        return contributions;
    }

    public String getEmail() {
        return email;
    }

    public String getFollowers() {
        return followers;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public String getFollowing() {
        return following;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getLocation() {
        return location;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<ArsenalListInfo.ListInfo> getOwnProjects() {
        return ownProjects;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public String getPublicRepo() {
        return publicRepo;
    }

    public String getPublicRepoUrl() {
        return publicRepoUrl;
    }

    public String getSite() {
        return site;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userInfoUrl);
        dest.writeString(this.nickname);
        dest.writeString(this.userName);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.email);
        dest.writeString(this.location);
        dest.writeString(this.site);
        dest.writeString(this.homepage);
        dest.writeString(this.followers);
        dest.writeString(this.followersUrl);
        dest.writeString(this.following);
        dest.writeString(this.followingUrl);
        dest.writeString(this.publicRepo);
        dest.writeString(this.publicRepoUrl);
        dest.writeTypedList(this.ownProjects);
        dest.writeTypedList(this.contributions);
    }

    protected ArsenalUserInfo(Parcel in) {
        this.userInfoUrl = in.readString();
        this.nickname = in.readString();
        this.userName = in.readString();
        this.portraitUrl = in.readString();
        this.email = in.readString();
        this.location = in.readString();
        this.site = in.readString();
        this.homepage = in.readString();
        this.followers = in.readString();
        this.followersUrl = in.readString();
        this.following = in.readString();
        this.followingUrl = in.readString();
        this.publicRepo = in.readString();
        this.publicRepoUrl = in.readString();
        this.ownProjects = in.createTypedArrayList(ArsenalListInfo.ListInfo.CREATOR);
        this.contributions = in.createTypedArrayList(ArsenalListInfo.ListInfo.CREATOR);
    }

    public static final Parcelable.Creator<ArsenalUserInfo> CREATOR = new Parcelable.Creator<ArsenalUserInfo>() {
        @Override
        public ArsenalUserInfo createFromParcel(Parcel source) {
            return new ArsenalUserInfo(source);
        }

        @Override
        public ArsenalUserInfo[] newArray(int size) {
            return new ArsenalUserInfo[size];
        }
    };
}
