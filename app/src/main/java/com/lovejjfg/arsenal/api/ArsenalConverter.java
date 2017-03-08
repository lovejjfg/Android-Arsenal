
package com.lovejjfg.arsenal.api;

import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class ArsenalConverter implements Converter<ResponseBody, List<ArsenalListInfo>> {

    /**
     * Factory for creating converter. We only care about decoding responses.
     **/
    public static final class Factory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                                Annotation[] annotations,
                                                                Retrofit retrofit) {
            Class aClass = getClass(type, 0);
            boolean assignableFrom = aClass.isAssignableFrom(ArsenalListInfo.class);
            if (assignableFrom) {
                return INSTANCE;
            }
            return null;
        }

        private static Class getClass(Type type, int i) {
            if (type instanceof ParameterizedType) { // 处理泛型类型
                System.out.println("1111111");
                return getGenericClass((ParameterizedType) type, i);
            } else if (type instanceof TypeVariable) {
                System.out.println("--------" + ((Class) getClass(((TypeVariable) type).getBounds()[0], 0)).getName());
                return (Class) getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
            } else {// class本身也是type，强制转型
                return (Class) type;
            }
        }

        private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
            Object genericClass = parameterizedType.getActualTypeArguments()[i];
            if (genericClass instanceof ParameterizedType) { // 处理多级泛型
                System.out.println("111111");
                return (Class) ((ParameterizedType) genericClass).getRawType();
            } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
                return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
            } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
                System.out.println("33333333");
                return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);
            } else {
                System.out.println("444444");
                return (Class) genericClass;
            }
        }

    }


    private ArsenalConverter() {
    }

    static final ArsenalConverter INSTANCE = new ArsenalConverter();

    private static final String HOST = "https://android-arsenal.com/";

    @Override
    public List<ArsenalListInfo> convert(ResponseBody value) throws IOException {
        final Elements listElements =
                Jsoup.parse(value.string(), HOST).select("div.project-info.clearfix");


        ArsenalListInfo info;
        ArrayList<ArsenalListInfo> infos = new ArrayList<>();
        for (Element e : listElements) {
            String title = null;
            String titleUrl = null;
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
                        titleUrl = first.attr("href").substring(1);
                    }

                    Elements select1 = tittle.select("a.tags");
                    if (!select1.isEmpty()) {
                        tag = select1.text();
                        tagUrl = select1.attr("href").substring(1);
                    }
                }
            }

            String freeDes = e.select("a.badge.free").text();
            String newDes = e.select("a.badge.new").text();
            badgeFree = freeDes == null;
            badgeNew = newDes == null;


            Elements select1 = e.select("div.desc > p");
            if (!select1.isEmpty()) {
                for (Element element : select1) {
//                    Element p1 = element.select("p").first();

                    Element img = element.select("img").first();
                    if (img != null) {
                        imgUrl = img.attr("data-layzr");
                        if (imgUrl == null || "".equals(imgUrl)) {
                        }
                    } else {
                        desc = element.text();
                    }
                }
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
                            userDetailUrl = a.attr("href").substring(1);
                        }
                    }

                }
            }
            info = new ArsenalListInfo(badgeFree, badgeNew, date, desc, imgUrl, isAndroid, isUser, tag, tagUrl, title, titleUrl, userDetailUrl, userName);
            infos.add(info);
        }
        return infos;
    }

}
