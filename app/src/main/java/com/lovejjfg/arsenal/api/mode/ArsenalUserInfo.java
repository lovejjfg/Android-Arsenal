package com.lovejjfg.arsenal.api.mode;

import java.util.ArrayList;

/**
 * Created by Joe on 2017/3/7.
 * Email lovejjfg@gmail.com
 */

public class ArsenalUserInfo {
    private String userInfoUrl;
    private String nickname;

    public ArsenalUserInfo(ArrayList<ArsenalListInfo> contributions,
                           String email, String followers,
                           String followersUrl, String following,
                           String followingUrl, String homepage,
                           String location, ArrayList<ArsenalListInfo> ownProjects,
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

    private ArrayList<ArsenalListInfo> ownProjects;
    private ArrayList<ArsenalListInfo> contributions;


}
