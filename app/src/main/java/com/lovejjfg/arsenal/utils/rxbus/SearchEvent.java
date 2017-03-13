package com.lovejjfg.arsenal.utils.rxbus;

/**
 * Created by Joe on 2017/3/13.
 * Email lovejjfg@gmail.com
 */

public class SearchEvent {
    public String key;
    public String tagName;
    public int type;

    public SearchEvent(String key, String tagName, int type) {
        this.key = key;
        this.tagName = tagName;
        this.type = type;
    }
}
