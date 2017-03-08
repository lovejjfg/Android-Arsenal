package com.lovejjfg.arsenal.api.mode;

/**
 * Created by Joe on 2017/3/7.
 * Email lovejjfg@gmail.com
 */

public class ArsenalListInfo {
    private String title;
    private String listDetailUrl;
    private String tagUrl;
    private String tag;

    private boolean badgeFree;
    private boolean badgeNew;

    private String desc;
    private String imgUrl;

    private String registeredDate;

    private boolean isAndroid;
    private boolean isUser;
    private String userName;
    private String userDetailUrl;

    public ArsenalListInfo(boolean badgeFree, boolean badgeNew,
                           String registeredDate, String desc, String imgUrl,
                           boolean isAndroid, boolean isUser, String tag,
                           String tagUrl, String title, String listDetailUrl,
                           String userDetailUrl, String userName) {
        this.badgeFree = badgeFree;
        this.badgeNew = badgeNew;
        this.registeredDate = registeredDate;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.isAndroid = isAndroid;
        this.isUser = isUser;
        this.tag = tag;
        this.tagUrl = tagUrl;
        this.title = title;
        this.listDetailUrl = listDetailUrl;
        this.userDetailUrl = userDetailUrl;
        this.userName = userName;
    }

    public boolean isBadgeFree() {
        return badgeFree;
    }

    public boolean isBadgeNew() {
        return badgeNew;
    }

    public String getDesc() {
        return desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public boolean isAndroid() {
        return isAndroid;
    }

    public boolean isUser() {
        return isUser;
    }

    public String getListDetailUrl() {
        return listDetailUrl;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public String getTag() {
        return tag;
    }

    public String getTagUrl() {
        return tagUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUserDetailUrl() {
        return userDetailUrl;
    }

    public String getUserName() {
        return userName;
    }
}
