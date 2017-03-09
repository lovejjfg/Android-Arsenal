package com.lovejjfg.arsenal.api.mode;

import android.os.Parcel;
import android.os.Parcelable;

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
     * Owner
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
    private String Owner;
    private String OwnerUrl;

    private String desc;

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
        dest.writeList(this.contributors);
        dest.writeString(this.updatedDate);
        dest.writeString(this.Owner);
        dest.writeString(this.OwnerUrl);
        dest.writeString(this.desc);
    }

    public ArsenalDetailInfo() {
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
        this.contributors = new ArrayList<ArsenalUserInfo>();
        in.readList(this.contributors, ArsenalUserInfo.class.getClassLoader());
        this.updatedDate = in.readString();
        this.Owner = in.readString();
        this.OwnerUrl = in.readString();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<ArsenalDetailInfo> CREATOR = new Parcelable.Creator<ArsenalDetailInfo>() {
        @Override
        public ArsenalDetailInfo createFromParcel(Parcel source) {
            return new ArsenalDetailInfo(source);
        }

        @Override
        public ArsenalDetailInfo[] newArray(int size) {
            return new ArsenalDetailInfo[size];
        }
    };
}
