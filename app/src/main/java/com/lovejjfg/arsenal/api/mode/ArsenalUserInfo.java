package com.lovejjfg.arsenal.api.mode;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Joe on 2017/3/7.
 * Email lovejjfg@gmail.com
 */

public class ArsenalUserInfo implements Parcelable {
    private String userInfoUrl;
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

    private String userName;
    private String portraitUrl;
    private String email;
    private String location;
    private String site;
    private String homepage;
    private String followers;
    private String followersUrl;
    private String following;
    private String followingUrl;
    private String publicRepo;
    private String publicRepoUrl;

    private ArrayList<ArsenalListInfo.ListInfo> ownProjects;
    private ArrayList<ArsenalListInfo.ListInfo> contributions;

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
