package org.live.baselib.http
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.live.baselib.BuildConfig
import org.live.test.http.intercept.ConnectivityInterceptor
import org.live.test.http.intercept.RewriteCacheControlInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by wl on 2018/10/14.
 */
object HttpApiManager {

    private val mHttpService: INonoHttp

    init {
        val isDebug = BuildConfig.DEBUG
        val loggingInterceptor = HttpLoggingInterceptor()
        if (isDebug) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(ConnectivityInterceptor())
                .addNetworkInterceptor(loggingInterceptor)
                .addNetworkInterceptor(RewriteCacheControlInterceptor())
                // .addNetworkInterceptor(CustomHeaderInterceptor())
                // .addNetworkInterceptor(CookieInterceptor())
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(HttpConst.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mHttpService = retrofit.create(INonoHttp::class.java)
    }

    fun getHttpService(): INonoHttp {
        return mHttpService
    }
}