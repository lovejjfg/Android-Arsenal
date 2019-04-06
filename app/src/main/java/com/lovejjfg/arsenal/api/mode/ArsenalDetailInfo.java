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

import android.support.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by Joe on 2017/3/7.
 * Email lovejjfg@gmail.com
 */

public class ArsenalDetailInfo implements Parcelable {

    /**
     * Category
     * Free
     * Tag
     * Animations
     * License
     * Apache License, Version 2.0
     * Min SDK
     * 7 (Android 2.1 Eclair)
     * Registered
     * Dec 5, 2016
     * Favorites
     * 2
     * Link
     * https://github.com/lovejjfg/Circle
     * See also
     * Rx2Animations
     * Android Material Transitions
     * FlipAnimation
     * PreLollipopTransition
     * AppIntroAnimation
     * Additional
     * <p>
     * Language
     * Java
     * Version
     * tag_v1.1 (Jun 30, 2016)
     * Created
     * Apr 3, 2016
     * Updated
     * Mar 2, 2017
     * owner
     * joe (lovejjfg)
     * Contributor
     * joe (lovejjfg)Activity
     * Badge
     * Generate
     * Download
     * Source code
     * APK file
     */
    private String title;
    private String titleUrl;
    private String category;
    private String tag;
    private String license;
    private String registeredDate;

    private String favoritesCount;
    private String link;
    private ArrayList<ArsenalListInfo> associated;
    private ArrayList<ArsenalUserInfo> contributors;
    private String updatedDate;
    private String owner;
    private String ownerurl;

    private String desc;

    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPortraitUrl() {

        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    private String portraitUrl;


    public ArrayList<ArsenalListInfo> getAssociated() {
        return associated;
    }

    public void setAssociated(ArrayList<ArsenalListInfo> associated) {
        this.associated = associated;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<ArsenalUserInfo> getContributors() {
        return contributors;
    }

    public void setContributors(ArrayList<ArsenalUserInfo> contributors) {
        this.contributors = contributors;
    }

    public static Creator<ArsenalDetailInfo> getCREATOR() {
        return CREATOR;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(String favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerurl() {
        return ownerurl;
    }

    public void setOwnerurl(String ownerurl) {
        this.ownerurl = ownerurl;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public ArsenalDetailInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.titleUrl);
        dest.writeString(this.category);
        dest.writeString(this.tag);
        dest.writeString(this.license);
        dest.writeString(this.registeredDate);
        dest.writeString(this.favoritesCount);
        dest.writeString(this.link);
        dest.writeTypedList(this.associated);
        dest.writeTypedList(this.contributors);
        dest.writeString(this.updatedDate);
        dest.writeString(this.owner);
        dest.writeString(this.ownerurl);
        dest.writeString(this.desc);
        dest.writeString(this.language);
        dest.writeString(this.portraitUrl);
    }

    protected ArsenalDetailInfo(Parcel in) {
        this.title = in.readString();
        this.titleUrl = in.readString();
        this.category = in.readString();
        this.tag = in.readString();
        this.license = in.readString();
        this.registeredDate = in.readString();
        this.favoritesCount = in.readString();
        this.link = in.readString();
        this.associated = in.createTypedArrayList(ArsenalListInfo.CREATOR);
        this.contributors = in.createTypedArrayList(ArsenalUserInfo.CREATOR);
        this.updatedDate = in.readString();
        this.owner = in.readString();
        this.ownerurl = in.readString();
        this.desc = in.readString();
        this.language = in.readString();
        this.portraitUrl = in.readString();
    }

    public static final Creator<ArsenalDetailInfo> CREATOR = new Creator<ArsenalDetailInfo>() {
        @Override
        public ArsenalDetailInfo createFromParcel(Parcel source) {
            return new ArsenalDetailInfo(source);
        }

        @Override
        public ArsenalDetailInfo[] newArray(int size) {
            return new ArsenalDetailInfo[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        try {
            return obj instanceof ArsenalDetailInfo && this.link.equals(((ArsenalDetailInfo) obj).link) ;
        } catch (Exception e) {
            return super.equals(obj);
        }
    }
}
