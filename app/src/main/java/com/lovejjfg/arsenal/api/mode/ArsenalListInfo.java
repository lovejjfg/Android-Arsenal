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
import java.util.HashMap;

/**
 * Created by Joe on 2017/3/7.
 * Email lovejjfg@gmail.com
 */

public class ArsenalListInfo implements Parcelable {


    public String getHasMore() {
        return hasMore;
    }

    public void setHasMore(String hasMore) {
        this.hasMore = hasMore;
    }

    public ArrayList<ListInfo> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<ListInfo> infos) {
        this.infos = infos;
    }

    private String hasMore;
    private ArrayList<ListInfo> infos;
    private HashMap<String, String> tags;

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public static class ListInfo implements Parcelable {

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

        public ListInfo(boolean badgeFree, boolean badgeNew,
                        String date, String desc, String imgUrl,
                        boolean isAndroid, boolean isUser,
                        String tag, String tagUrl,
                        String title, String listDetailUrl,
                        String userDetailUrl, String userName) {
            this.userName = userName;
            this.badgeFree = badgeFree;
            this.badgeNew = badgeNew;
            this.desc = desc;
            this.imgUrl = imgUrl;
            this.isAndroid = isAndroid;
            this.isUser = isUser;
            this.listDetailUrl = listDetailUrl;
            this.registeredDate = date;
            this.tag = tag;
            this.tagUrl = tagUrl;
            this.title = title;
            this.userDetailUrl = userDetailUrl;
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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.listDetailUrl);
            dest.writeString(this.tagUrl);
            dest.writeString(this.tag);
            dest.writeByte(this.badgeFree ? (byte) 1 : (byte) 0);
            dest.writeByte(this.badgeNew ? (byte) 1 : (byte) 0);
            dest.writeString(this.desc);
            dest.writeString(this.imgUrl);
            dest.writeString(this.registeredDate);
            dest.writeByte(this.isAndroid ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isUser ? (byte) 1 : (byte) 0);
            dest.writeString(this.userName);
            dest.writeString(this.userDetailUrl);
        }

        public ListInfo() {
        }

        ListInfo(Parcel in) {
            this.title = in.readString();
            this.listDetailUrl = in.readString();
            this.tagUrl = in.readString();
            this.tag = in.readString();
            this.badgeFree = in.readByte() != 0;
            this.badgeNew = in.readByte() != 0;
            this.desc = in.readString();
            this.imgUrl = in.readString();
            this.registeredDate = in.readString();
            this.isAndroid = in.readByte() != 0;
            this.isUser = in.readByte() != 0;
            this.userName = in.readString();
            this.userDetailUrl = in.readString();
        }

        public static final Creator<ListInfo> CREATOR = new Creator<ListInfo>() {
            @Override
            public ListInfo createFromParcel(Parcel source) {
                return new ListInfo(source);
            }

            @Override
            public ListInfo[] newArray(int size) {
                return new ListInfo[size];
            }
        };
    }


    public ArsenalListInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hasMore);
        dest.writeTypedList(this.infos);
        dest.writeSerializable(this.tags);
    }

    protected ArsenalListInfo(Parcel in) {
        this.hasMore = in.readString();
        this.infos = in.createTypedArrayList(ListInfo.CREATOR);
        this.tags = (HashMap<String, String>) in.readSerializable();
    }

    public static final Creator<ArsenalListInfo> CREATOR = new Creator<ArsenalListInfo>() {
        @Override
        public ArsenalListInfo createFromParcel(Parcel source) {
            return new ArsenalListInfo(source);
        }

        @Override
        public ArsenalListInfo[] newArray(int size) {
            return new ArsenalListInfo[size];
        }
    };
}
