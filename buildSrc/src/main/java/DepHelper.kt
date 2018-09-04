/**
 * Created by wl on 2018/9/4.
 */
object Android {
    val compileSdkVersion = 28
    val buildToolsVersion = "28.0.1"
    val minSdkVersion = 17
    val targetSdkVersion = 23
    val versionCode = 1
    val versionName = "1.0.0"
    val applicationId = "com.live.nonokt"

    val androidSupportSdkVersion = "27.1.0"
}

object DepHelper {
    val appcompat_v7 = "com.android.support:appcompat-v7:${Android.androidSupportSdkVersion}"
    val constraint_layout = "com.android.support.constraint:constraint-layout:1.1.2"
    val design = "com.android.support:design:${Android.androidSupportSdkVersion}"
    val support_v4 = "com.android.support:support-v4:${Android.androidSupportSdkVersion}"
    val recyclerview_v7 = "com.android.support:recyclerview-v7:${Android.androidSupportSdkVersion}"
    val cardview_v7 = "com.android.support:cardview-v7:${Android.androidSupportSdkVersion}"
    val multidex = "com.android.support:multidex:1.0.2"
}