package com.lovejjfg.arsenal;

import android.text.Html;
import android.text.Spanned;

import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

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

        ArrayList<ArsenalListInfo> ownProjects = new ArrayList<>();

        ArrayList<ArsenalListInfo> contributions = new ArrayList<>();
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
                            ownProjects.add(new ArsenalListInfo(true, false, null, null, null, true, true, null, null, element2.text(), infoUrl, userInfoUrl, userName));
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
}