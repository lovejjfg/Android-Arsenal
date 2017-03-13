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

package com.lovejjfg.arsenal;

import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /**
     * <div class="pc">
     * <div class="project-info clearfix">
     * <div class="header">
     * <div class="title">
     * <a href="/details/1/5391">CircularImageClick</a>
     * <a class="tags" href="/tag/13">Buttons</a>
     * </div>
     * <a class="badge free" href="/free">Free</a>
     * <a class="badge new" href="/recent">New</a>
     * </div>
     * <div class="desc">
     * <p>A custom <code>ImageButton</code> that invoke <code>onClickListener</code> only when touch is inside the circle not outside (rectangle area of button).</p>
     * </div>
     * <div class="ftr l">
     * <i class="fa fa-calendar"></i> Mar 4, 2017
     * </div>
     * <div class="ftr r">
     * <i class="fa fa-user"></i>
     * <a href="/user/ahmed-basyouni">ahmed-basyouni</a>
     * </div>
     * </div>
     * </div>
     *
     * @throws IOException
     */
    @Test
    public void testJsoup() throws IOException {
        Document document = Jsoup.connect("https://android-arsenal.com").get();
//        Elements select = document.select("div.container.content");

        Elements select = document.select("div.project-info.clearfix");
        for (Element e : select) {
            String title;
            String titleUrl;
            String tagUrl;
            String tag;

            boolean badgeFree = false;
            boolean badgeNew = false;
            String desc;
            String imgUrl;
            String date;
            boolean isAndroid = false;
            boolean isUser = false;
            String userName;
            String userDetailUrl;
            if (!e.select("div.title.aa-ads-title").isEmpty()) {
                continue;
            }
            Elements elements = e.select("div.title");
            if (!elements.isEmpty()) {
                for (Element tittle : elements) {
                    Element first = tittle.select("a[href]").first();
                    if (first != null) {
                        title = first.text();
                        titleUrl = first.attr("href");
                        System.out.println("名称：" + title);
                        System.out.println("具体地址：" + titleUrl);
                    }

                    Elements select1 = tittle.select("a.tags");
                    if (!select1.isEmpty()) {
                        tag = select1.text();
                        tagUrl = select1.attr("href");
                        System.out.println("tags:" + tag);
                        System.out.println("tagUrl:" + tagUrl);
                    }
                }
            }

            String freeDes = e.select("a.badge.free").text();
            String newDes = e.select("a.badge.new").text();
            System.out.println("free:" + freeDes);
            System.out.println("new:" + newDes);
            badgeFree = freeDes == null;
            badgeNew = newDes == null;


//            Elements select11 = e.select("div.desc");
//            String text = select11.first().toString();
//            Spanned spanned = Html.fromHtml(text);
//            System.out.println("全部的描述：" + spanned);
            Elements select1 = e.select("div.desc > p");
            if (!select1.isEmpty()) {
                for (Element element : select1) {
//                    Element p1 = element.select("p").first();

                    Element img = element.select("img").first();
                    if (img != null) {
                        imgUrl = img.attr("data-layzr");
                        System.out.println("描述中的图片url：" + imgUrl);
                    } else {
                        desc = element.toString();
                        System.out.println("描述信息：" + desc);
                    }
                }
            }

            //日期
//            e.select("i.fa.fa-calendar").first();
            date = e.select("div.ftr.l").first().text();
            System.out.println("日期：" + date);

            //apk user
            Elements select2 = e.select("div.ftr.r ");
            if (!select2.isEmpty()) {
                for (Element element : select2) {
                    if (!element.select("i.fa.fa-android").isEmpty()) {
                        isAndroid = true;
                        System.out.println("是Android应用！");
                    }
                    if (!element.select("i.fa.fa-user").isEmpty()) {
                        isUser = true;
                        System.out.println("是用户！");
                        Elements a = element.select("a");
                        if (!a.isEmpty()) {
                            userName = a.text();
                            userDetailUrl = a.attr("href");
                            System.out.println("用户：" + a.text());
                            System.out.println("用户地址：" + a.attr("href"));
                        }
                    }

                }
            }

            System.out.println("---------------------------------------");
        }

    }

    @Test
    public void testArsenalUserInfo() throws IOException {
        String userInfoUrl;
        String nickname;
        String userName;
        String portraitUrl;
        String email;
        String location;
        String site;
        String homepage;
        String followers;
        String followersUrl;
        String following;
        String followingUrl;
        String publicRepo;
        String publicRepoUrl;

        ArrayList<ArsenalListInfo.ListInfo> ownProjects = new ArrayList<>();

        ArrayList<ArsenalListInfo.ListInfo> contributions = new ArrayList<>();
        Document document = Jsoup.connect("https://android-arsenal.com/user/lovejjfg").get();
        Elements select = document.select("div.project-details.vcard");
        for (Element element : select) {
            userName = element.select("a[href]").first().text();
            System.out.println("name: " + userName);
            userInfoUrl = element.select("a").first().attr("href");
            System.out.println("url: " + userInfoUrl);
            String attr = element.select("img").attr("src");
            portraitUrl = attr.substring(0, attr.lastIndexOf("?"));
            System.out.println("头像: " + portraitUrl);

            email = element.select("a.email").text();
            System.out.println("email: " + email);
            site = element.select("dt:contains(Site) + dd").text();
            System.out.println("site: " + site);

            location = element.select("dt:contains(Location) + dd").first().text();
            System.out.println("location:" + location);
            String language = element.select("dt:contains(Language) + dd").first().text();
            System.out.println("language:" + language);
            homepage = element.select("dt:contains(Homepage) + dd").first().text();
            System.out.println("Homepage:" + homepage);
            Elements select2 = element.select("dt:contains(Followers) + dd");
            followers = select2.first().text();
            followersUrl = select2.first().select("a").first().attr("href");
            System.out.println("attr1:" + followersUrl);
            System.out.println("Followers:" + followers);
            Elements select1 = element.select("dt:contains(Following) + dd");
            following = select1.first().text();
            followingUrl = select1.first().select("a").first().attr("href");
            System.out.println("Following:" + following);
            System.out.println("FollowingUrl:" + followingUrl);
            Elements select4 = element.select("dt:contains(Public.repo(s)) + dd");
            publicRepo = select4.first().text();
            publicRepoUrl = select4.first().select("a").first().attr("href");
            System.out.println("PublicRepo: " + publicRepo);
            System.out.println("PublicRepoUrl: " + publicRepoUrl);
            element.select("div.moduletable_events > ul");
            Elements h2Tags = element.select("h2");
            for (Element e : h2Tags) {
                System.out.println("call: " + e.toString());
                if (e.text().contains("Own projects")) {
                    Element element1 = e.nextElementSibling();
//                    System.out.println("这是下一个：" + element1.toString());
                    Elements select3 = element1.select("dl > ul >li");
                    if (!select3.isEmpty()) {
                        for (Element element2 : select3) {
                            String infoUrl = element2.select("a").first().attr("href");
                            System.out.println("name:" + element2.text() + ";;href:" + infoUrl);
                            ownProjects.add(new ArsenalListInfo.ListInfo(true, false, null, null, null, true, true, null, null, element2.text(), infoUrl, userInfoUrl, userName));

                        }
                    }
                }
                if (e.text().contains("Contributions")) {
                    Element element1 = e.nextElementSibling();
//                    System.out.println("这是下一个：" + element1.toString());
                    Elements select3 = element1.select("dl > ul >li");
                    if (!select3.isEmpty()) {
                        for (Element element2 : select3) {
                            System.out.println("name:" + element2.text() + ";;href:" + element2.select("a").first().attr("href"));
                        }
                    }
                }
//                            Elements ul = e.select("div.moduletable_events > ul");
//                Elements li = e.select("li");
//                for (Element l : li) {
//                    System.out.println("call: " + l.toString());
//                }

            }
        }
    }

    @Test
    public void testSearch() throws IOException {
        Document document = Jsoup.connect("https://android-arsenal.com/search?page=1&q=circle").get();
        Elements select1 = document.select("a.after-btn");
        if (select1.isEmpty()) {
            System.out.println("没有更多了！");
        } else {
            System.out.println(select1.first().attr("href"));
        }
    }

    @Test
    public void testSearch1() throws IOException {
        Document document = Jsoup.connect("https://android-arsenal.com/search?page=1&q=circle").get();
        Elements select1 = document.select("a.after-btn");
        if (select1.isEmpty()) {
            System.out.println("没有更多了！");
        } else {
            System.out.println(select1.first().attr("href"));
        }
    }

    @Test
    public void testDetail() throws IOException {
        Document document = Jsoup.connect("https://android-arsenal.com/details/1/5430").get();
        Elements select1 = document.select("div#projectDesc");
        if (select1.isEmpty()) {
            System.out.println("没有更多了！");
        } else {
            System.out.println(select1.first().text());
        }
    }

    @Test
    public void testRe() throws IOException {
        String text = "xxadsxx";
        boolean matches = text.matches("(?!ads).*$");
        System.out.println("是否匹配：" + matches);
    }

    @Test
    public void testGetJs() throws IOException {
        Document document = Jsoup.connect("https://android-arsenal.com").get();
//        Elements select = document.select("div.container.content");
        final Elements script =
                document.select("script");
        for (Element ee : script) {
            String text = ee.data();
            if (text != null && text.contains("ALL_TAGS")) {
                int end = text.indexOf("},");
                int start = text.indexOf("ALL_TAGS={");
                text = (String) text.subSequence(start + "ALL_TAGS={".length(), end);
                System.out.println();
                String[] split = text.split(",");
                HashMap<String, String> hashMap = new HashMap<>();
                for (int i = 0; i < split.length; i++) {
                    String[] library = split[i].split(":");
                    hashMap.put(library[0], library[1]);
                }
                System.out.println(split.length);

            }

        }
    }

    @Test
    public void testAds() throws IOException {
        String text = "<div class=\"header\">\n" +
                "    <div class=\"title\">\n" +
                "     <a href=\"/details/1/5428\">DateTimeTemplate</a>\n" +
                "     <a class=\"tags\" href=\"/tag/196\">Text Formatting</a>\n" +
                "    </div>\n" +
                "    <a class=\"badge free\" href=\"/free\">Free</a>\n" +
                "    <a class=\"badge new\" href=\"/recent\">New</a>\n" +
                "   </div>";
        Document document = Jsoup.connect("https://android-arsenal.com/details/1/5415").get();
//        Document document = Jsoup.parse(text);

//        Elements select1 = document.body().select("ul#tabs");//.not("div[class*=ads]");
        Elements select1 = document.body().select("div.tab-content");//.not("div[class*=ads]");
//        Elements select1 =  document.chi().not(".title");//.not("div[class*=ads]");
        for (Element e : select1) {
            System.out.println(".....");
            System.out.println(e.toString());

        }
//            System.out.println(select1.first().toString());

    }

    @Test
    public void testDetailInfo() throws IOException {

        ArsenalDetailInfo info = new ArsenalDetailInfo();
        //
        Document document = Jsoup.connect("https://android-arsenal.com/details/1/5415").get();
        Element first = document.select("div.col-md-2.contributor").first();
        Element h1 = document.select("h1").first();
        Element ssa = h1.select("a#favoriteButton").first();
        Element element = ssa.nextElementSibling();
        String title = element.text();
        String href = element.attr("href");
        System.out.println("title:" + title);
        System.out.println("href:" + href);
        if (first != null) {
            String userDetail = first.select("a[href]").attr("href");
            System.out.println(userDetail);
            String portraitUrl = first.select("img[src]").attr("src");
            System.out.println(portraitUrl);
        }
        Element fav = document.select("dd#afavCount").first();
        String facConut = fav.text();
        String link = fav.nextElementSibling().nextElementSibling().text();
        System.out.println("linkk::" + link);
        System.out.println(facConut);
//        Additional
        Elements h2Tags = document.select("h2");
        for (Element e : h2Tags) {

            if (e.text().contains("General")) {
                Element element1 = e.nextElementSibling();
                Elements select3 = element1.select("dl > dt ");

            }
            if (e.text().contains("Additional")) {
                Element element1 = e.nextElementSibling();
                Elements select3 = element1.select("dl > dt ");
                if (!select3.isEmpty()) {
                    for (Element element2 : select3) {
                        if ("Language".equals(element2.text())) {
                            String language = element2.nextElementSibling().text();
                            info.setLanguage(language);
                        }
                        if ("Updated".equals(element2.text())) {
                            String updated = element2.nextElementSibling().text();
                            info.setUpdatedDate(updated);
                        }
                        if ("Owner".equals(element2.text())) {
                            String owner = element2.nextElementSibling().text();
                            info.setUpdatedDate(owner);
                        }
                    }
                }
            }
        }

    }

}