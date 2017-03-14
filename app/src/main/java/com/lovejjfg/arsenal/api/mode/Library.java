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

/**
 * Created by Joe on 2017/3/14.
 * Email lovejjfg@gmail.com
 */

public class Library {

    private String name;

    public String getDes() {
        return des;
    }

    public Library(String name, String des, String jumpUrl) {
        this.des = des;
        this.jumpUrl = jumpUrl;
        this.name = name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String des;
    private String jumpUrl;
}
