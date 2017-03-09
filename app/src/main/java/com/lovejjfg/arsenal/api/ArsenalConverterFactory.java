
package com.lovejjfg.arsenal.api;

import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class ArsenalConverterFactory extends Converter.Factory {
    private static final ArsenalListInfoConverter ARSENAL_LIST_INFO_CONVERTER = new ArsenalListInfoConverter();
    private static final ArsenalUserInfoConverter ARSENAL_USER_INFO_CONVERTER = new ArsenalUserInfoConverter();

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {

        Class aClass = getClass(type, 0);
        if (aClass.isAssignableFrom(ArsenalListInfo.class)) {
            return ARSENAL_LIST_INFO_CONVERTER;
        }
        if (aClass.isAssignableFrom(ArsenalUserInfo.class)) {
            return ARSENAL_USER_INFO_CONVERTER;
        }
        return null;
    }

    private static Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return (Class) type;
        }
    }

    private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }

    private static final String HOST = "https://android-arsenal.com/";

    static ArsenalConverterFactory create() {
        return new ArsenalConverterFactory();
    }

    private static class ArsenalListInfoConverter implements Converter<ResponseBody, ArsenalListInfo> {
        @Override
        public ArsenalListInfo convert(ResponseBody value) throws IOException {
            Document parse = Jsoup.parse(value.string(), HOST);
            final Elements listElements =
                    parse.select("div.project-info.clearfix");


            ArsenalListInfo arsenalListInfo = new ArsenalListInfo();
            ArsenalListInfo.ListInfo info;
            ArrayList<ArsenalListInfo.ListInfo> infos = new ArrayList<>();
            arsenalListInfo.setInfos(infos);
            Elements hasMore = parse.select("a.after-btn");
            arsenalListInfo.setHasMore(hasMore.isEmpty() ? null : hasMore.first().attr("href"));

            for (Element e : listElements) {
                String title = null;
                String listDetailUrl = null;
                String tagUrl = null;
                String tag = null;

                boolean badgeFree = false;
                boolean badgeNew = false;
                String desc = null;
                String imgUrl = null;
                String date = null;
                boolean isAndroid = false;
                boolean isUser = false;
                String userName = null;
                String userDetailUrl = null;
                if (!e.select("div.title.aa-ads-title").isEmpty()) {
                    continue;
                }
                Elements elements = e.select("div.title");
                if (!elements.isEmpty()) {
                    for (Element tittle : elements) {
                        Element first = tittle.select("a[href]").first();
                        if (first != null) {
                            title = first.text();
                            listDetailUrl = getHref(first);
                        }

                        Elements select1 = tittle.select("a.tags");
                        if (!select1.isEmpty()) {
                            tag = select1.text();
                            tagUrl = getHref(select1);
                        }
                    }
                }

                String freeDes = e.select("a.badge.free").text();
                String newDes = e.select("a.badge.new").text();
                badgeFree = !TextUtils.isEmpty(freeDes);
                badgeNew = !TextUtils.isEmpty(newDes);

                Elements select11 = e.select("div.desc");
                String text = select11.first().toString();
                Spanned spanned = Html.fromHtml(text);
                Log.e("TAG", "convert: " + spanned.toString());
                Elements select1 = e.select("div.desc > p");
                if (!select1.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (Element element : select1) {
//                    Element p1 = element.select("p").first();

                        Element img = element.select("img").first();
                        if (img != null) {
                            imgUrl = img.attr("data-layzr");
                        } else {
                            sb.append(element.text());
                        }
                    }
                    desc = sb.toString();
                }

                //日期
//            e.select("i.fa.fa-calendar").first();
                date = e.select("div.ftr.l").first().text();

                //apk user
                Elements select2 = e.select("div.ftr.r ");
                if (!select2.isEmpty()) {
                    for (Element element : select2) {
                        if (!element.select("i.fa.fa-android").isEmpty()) {
                            isAndroid = true;
                        }
                        if (!element.select("i.fa.fa-user").isEmpty()) {
                            isUser = true;
                            Elements a = element.select("a");
                            if (!a.isEmpty()) {
                                userName = a.text();
                                userDetailUrl = getHref(a);
                            }
                        }

                    }
                }
                info = new ArsenalListInfo.ListInfo(badgeFree, badgeNew, date, desc, imgUrl, isAndroid, isUser, tag, tagUrl, title, listDetailUrl, userDetailUrl, userName);

                infos.add(info);
            }
            return arsenalListInfo;
        }
    }

    @NonNull
    private static String getHref(Element first) {
        String listDetailUrl;
        String href = first.attr("href");
        listDetailUrl = href.substring(href.lastIndexOf("/") + 1);
        return listDetailUrl;
    }

    @NonNull
    private static String getHref(Elements select1) {
        String tagUrl;
        String href = select1.attr("href");
        tagUrl = href.substring(href.lastIndexOf("/") + 1);
        return tagUrl;
    }

    private static final class ArsenalDetaiInfoConverter implements Converter<ResponseBody, ArsenalDetailInfo> {
        @Override
        public ArsenalDetailInfo convert(ResponseBody value) throws IOException {
            return null;
        }
    }

    private static final class ArsenalUserInfoConverter implements Converter<ResponseBody, ArsenalUserInfo> {
        @Override
        public ArsenalUserInfo convert(ResponseBody value) throws IOException {
            String userInfoUrl = null;
            String nickname = null;
            String userName = null;
            String portraitUrl = null;
            String email = null;
            String location = null;
            String site = null;
            String homepage = null;
            String followers = null;
            String followersUrl = null;
            String following = null;
            String followingUrl = null;
            String publicRepo = null;
            String publicRepoUrl = null;

            ArrayList<ArsenalListInfo.ListInfo> ownProjects = new ArrayList<>();

            ArrayList<ArsenalListInfo.ListInfo> contributions = new ArrayList<>();
            final Elements select =
                    Jsoup.parse(value.string(), HOST).select("div.project-details.vcard");
            for (Element element : select) {
                userName = getText(element.select("a[href]"));
                userInfoUrl = element.select("a").first().attr("href");
                portraitUrl = element.select("img").attr("src");
//                portraitUrl = attr.substring(0, attr.lastIndexOf("?"));

                email = element.select("a.email").text();
                site = element.select("dt:contains(Site) + dd").text();

                location = element.select("dt:contains(Location) + dd").first().text();
                String language = element.select("dt:contains(Language) + dd").first().text();
                homepage = element.select("dt:contains(Homepage) + dd").first().text();
                Elements select2 = element.select("dt:contains(Followers) + dd");
                followers = getText(select2);
                followersUrl = getNormalHref(select2);
                Elements select1 = element.select("dt:contains(Following) + dd");
                following = getText(select1);
                followingUrl = getNormalHref(select1);
                Elements select4 = element.select("dt:contains(Public repo) + dd");
                publicRepo = getText(select4);
                publicRepoUrl = getNormalHref(select4);
                element.select("div.moduletable_events > ul");
                Elements h2Tags = element.select("h2");
                for (Element e : h2Tags) {
                    if (e.text().contains("Own projects")) {
                        Element element1 = e.nextElementSibling();
                        Elements select3 = element1.select("dl > ul >li");
                        if (!select3.isEmpty()) {
                            for (Element element2 : select3) {
                                if (element2.select("a") != null && element2.select("a").first() != null) {
                                    String infoUrl = element2.select("a").first().attr("href");
                                    ownProjects.add(new ArsenalListInfo.ListInfo(true, false, null, null, null, true, true, null, null, element2.text(), infoUrl, userInfoUrl, userName));
                                }
                            }
                        }
                    }
                    if (e.text().contains("Contributions")) {
                        Element element1 = e.nextElementSibling();
                        Elements select3 = element1.select("dl > ul >li");
                        if (!select3.isEmpty()) {
                            for (Element element2 : select3) {
                                if (element2.select("a") != null && element2.select("a").first() != null) {
                                    String infoUrl = element2.select("a").first().attr("href");
                                    contributions.add(new ArsenalListInfo.ListInfo(true, false, null, null, null, true, true, null, null, element2.text(), infoUrl, userInfoUrl, userName));
                                }
                            }
                        }
                    }
                }
            }
            return new ArsenalUserInfo(contributions, email, followers,
                    followersUrl, following, followingUrl,
                    homepage, location, ownProjects, portraitUrl,
                    publicRepo, publicRepoUrl, site, userInfoUrl, userName);
        }
    }

    private static String getText(Elements select4) {
        Element element3 = select4.first();
        if (element3 != null) {
            return element3.text();
        }
        return null;
    }

    private static String getNormalHref(Elements select2) {
        if (select2 == null || select2.first() == null || select2.first().select("a") == null) {
            return null;
        }
        Element a = select2.first().select("a").first();

        return a != null ? a.attr("href") : null;
    }


}
