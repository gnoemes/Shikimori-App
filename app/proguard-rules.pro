# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-allowaccessmodification
-dontpreverify

-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn com.pushtorefresh.storio2.**
-dontwarn com.squareup.picasso.**
-dontwarn okhttp3.**
-dontnote android.net.http.**
-dontnote org.apache.http.**

-dontwarn com.google.errorprone.annotations.**

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keep class org.jsoup.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.app.** { *; }
-keep interface android.support.v7.app.** { *; }

-keepclassmembers enum * { *; }
-keep class com.gnoemes.shikimoriapp.entity.anime.** { *; }
-keep class com.gnoemes.shikimoriapp.entity.manga.** { *; }
-keep class com.gnoemes.shikimoriapp.entity.common.** { *; }
-keep class com.gnoemes.shikimoriapp.entity.rates.** { *; }
-keep class com.gnoemes.shikimoriapp.entity.user.data.** { *; }
-keep class com.gnoemes.shikimoriapp.entity.calendar.data.** { *; }
-keep class com.gnoemes.shikimoriapp.entity.club.data.** { *; }
-keep class com.gnoemes.shikimoriapp.entity.topic.data.** { *; }

-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep class okhttp3.internal.publicsuffix.PublicSuffixDatabase