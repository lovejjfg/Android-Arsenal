# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#指定压缩级别
-optimizationpasses 5

#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers

#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute Unknown
#保留行号
-keepattributes SourceFile,LineNumberTable

#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#实体类是否被混淆
#-keep class com.ejupay.sdk.core.** { *; }

##Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
#-keep public class * extends android.support.v4.app.Fragment
#-keep public class * extends android.app.Fragment

# 保持测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**

# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**


#retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
#-dontwarn retrofit2.OkHttpCall
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-dontwarn javax.annotation.**
# keep anotation
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontwarn javax.annotation.**

# lambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

-keepattributes InnerClasses
-dontwarn InnerClasses
-dontwarn InnerClasses$*
# lambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

-keepattributes InnerClasses
-dontwarn InnerClasses
-dontwarn InnerClasses$*

# WeChat
-dontwarn com.tencent.**
#-keep class com.tencent.** { *; }
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}
#alipay
-dontwarn com.alipay.**
-keep class com.alipay.** { *; }

# SView 如果需要所有方法都保留，那么需要加‘{*; }’ 不然的话未使用的方法将会被删除
#-keep class com.lovejjfg.sview.utils.ToastUtil{ *; }
-keep class com.lovejjfg.sview.utils.ShakeHelper




# Glide specific rules #
# https://github.com/bumptech/glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}


## GSON 2.2.4 specific rules ##

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
#jsoup
-keeppackagenames org.jsoup.nodes

# 如果是反射创建的对象要保证其相关构造方法不被删除
#-keepclassmembers class * extends com.ejupay.sdk.base.IBasePresenter {
#     <init>(...);
#}


# android-lite-orm
-keep public class com.litesuits.orm.LiteOrm { *; }
# keep anotation
-keepclasseswithmembers class * {
    @com.litesuits.orm.db.annotation.* <fields>;
}

-assumenosideeffects class android.util.Log {
    public static *** i(...);
    public static *** v(...);
    public static *** println(...);
    public static *** w(...);
    public static *** wtf(...);
}



