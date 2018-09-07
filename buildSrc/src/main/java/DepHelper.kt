/**
 * Created by wl on 2018/9/4.
 *
 * 注意:
 * 在library或app中使用kapt时候的前提是 apply plugin: 'kotlin-android'
 * 如果没有则使用默认的annotationProcessor代替
 */
object Android {
    val compileSdkVersion = 28
    val buildToolsVersion = "28.0.1"
    val minSdkVersion = 17
    val targetSdkVersion = 23
    val versionCode = 1
    val versionName = "1.0.0"
    val applicationId = "org.live.nonokt"

    val androidSupportSdkVersion = "27.1.0"
    val archLifecycleVersion = "1.0.0"
    val archRoomVersion = "1.0.0"
    val butterknife = "8.8.1"
    val kotlin_version = "1.2.61"
}

object DepHelper {
    val gradle_wrapper = "https\\://services.gradle.org/distributions/gradle-4.4-all.zip"
    val plugin_gradle = "com.android.tools.build:gradle:3.1.4"

    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Android.kotlin_version}"
    val plugin_kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Android.kotlin_version}"

    // support
    val support_v4 = "com.android.support:support-v4:${Android.androidSupportSdkVersion}"
    val appcompat_v7 = "com.android.support:appcompat-v7:${Android.androidSupportSdkVersion}"
    val constraint_layout = "com.android.support.constraint:constraint-layout:1.1.2"
    var support_annotations = "com.android.support:support-annotations:24.0.0"
    var junit = "junit:junit:4.12"
    var runner = "com.android.support.test:runner:1.0.1"
    var espresso_core = "com.android.support.test.espresso:espresso-core:3.0.1"

    // md
    val design = "com.android.support:design:${Android.androidSupportSdkVersion}"
    val recyclerview_v7 = "com.android.support:recyclerview-v7:${Android.androidSupportSdkVersion}"
    val cardview_v7 = "com.android.support:cardview-v7:${Android.androidSupportSdkVersion}"

    val multidex = "com.android.support:multidex:1.0.2"

    // Leak Canary
    val leakcanary_debug = "com.squareup.leakcanary:leakcanary-android:1.5.4"
    val leakcanary_release = "com.squareup.leakcanary:leakcanary-android-no-op:1.5.4"

    // eventbus
    var eventbus = "org.greenrobot:eventbus:3.0.0"

    // lifecycle
    val lifecycle_runtime = "android.arch.lifecycle:runtime:${Android.archLifecycleVersion}"
    val lifecycle_extensions = "android.arch.lifecycle:extensions:${Android.archLifecycleVersion}"
    val annotationProcessor_lifecycle_compiler = "android.arch.lifecycle:compiler:${Android.archLifecycleVersion}"
    // lifecycle_room
    var lifecycle_common_room = "android.arch.persistence.room:common:${Android.archRoomVersion}"
    var lifecycle_runtime_room = "android.arch.persistence.room:runtime:${Android.archRoomVersion}"
    var annotationProcessor_lifecycle_compiler_room = "android.arch.persistence.room:compiler:${Android.archRoomVersion}"

    // rxjava2
    var rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
    var rxjava_okhttp = "com.squareup.okhttp3:okhttp:3.9.0"
    var rxjava_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:3.9.0"
    var rxjava_android ="io.reactivex.rxjava2:rxandroid:2.0.1"
    var rxjava = "io.reactivex.rxjava2:rxjava:2.1.3"
    var rxjava_retrofit2 = "com.squareup.retrofit2:retrofit:2.3.0"
    var rxjava_retrofit2_converter = "com.squareup.retrofit2:converter-gson:2.3.0"

    // rxjava1
    var rxjava_adapter_v1 = "com.squareup.retrofit2:adapter-rxjava:2.0.2"
    var rxjava_okhttp_v1 = "com.squareup.okhttp3:okhttp:3.9.0"
    var rxjava_logging_interceptor_v1 = "com.squareup.okhttp3:logging-interceptor:3.9.0"
    var rxjava_android_v1 ="io.reactivex:rxandroid:1.2.1"
    var rxjava_v1 = "io.reactivex:rxjava:1.3.0"
    var rxjava_retrofit_v1 = "com.squareup.retrofit2:retrofit:2.1.0"
    var rxjava_retrofit_converter_v1 = "com.squareup.retrofit2:converter-gson:2.0.2"

    var gson = "com.google.code.gson:gson:2.8.0"

    // ele 超赞的UE 工具
    var ele_ue_debug = "me.ele:uetool:1.0.15"
    var ele_ue_release = "me.ele:uetool-no-op:1.0.15"
    // if you want to show more attrs about Fresco's DraweeView
    var ele_ue_fresco_debug = "me.ele:uetool-fresco:1.0.15"

    // java-web-socket
    var java_websocket = "org.java-websocket:Java-WebSocket:1.3.9"

    // butterknife(app模块不需要应用插件)
    var butterknife = "com.jakewharton:butterknife:${Android.butterknife}"
    var annotationProcessor_butterknife = "com.jakewharton:butterknife-compiler:${Android.butterknife}"

    // router
    var arouter_api = "com.alibaba:arouter-api:1.2.4"
    var arouter_compiler = "com.alibaba:arouter-compiler:1.1.4"
    var arouter_annotation = "com.alibaba:arouter-annotation:1.0.4"

    // logger
    var logger = "com.orhanobut:logger:2.1.1"

    // glide
    var glide = "com.github.bumptech.glide:glide:4.5.0"
    var glide_integration = "com.github.bumptech.glide:okhttp3-integration:4.5.0"
    var glide_transformations = "jp.wasabeef:glide-transformations:2.0.0"

    // AndResGuard
    var plugin_and_res_guard = "com.tencent.mm:AndResGuard-gradle-plugin:1.2.14"

    // photoview
    var photoview = "com.github.chrisbanes.photoview:library:1.2.4"

    // greendao
    var greendao = "org.greenrobot:greendao:3.2.2"

    // baseRecyclerViewAdapterHelper
    var BRVAH = "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.22"

    // 6.0权限管理
    var easypermissions = "pub.devrel:easypermissions:1.3.0"

    // MultiType
    var multi_type = "me.drakeet.multitype:multitype:3.4.4"

    // 图片选择器
    var zhihu_matisse = "com.zhihu.android:matisse:0.4.3"

    // banner
    var banner = "com.youth.banner:banner:1.4.10"
}