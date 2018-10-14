package org.live.test.http.intercept;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wl on 2018/10/14.
 * 自定义Header 追加到每个请求中用于验证
 */
public class CustomHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String clientInfo = "client info";
        Request.Builder requestBuilder = chain.request().newBuilder()
                .addHeader("Accept", "application/vnd.kw.1.0.0+json")
                .addHeader("ClientInfo", clientInfo);
        String token = "token xx";
        if (!TextUtils.isEmpty(token)) {
            requestBuilder.addHeader("Authorization", "Bearer " + token);
        }
        return chain.proceed(requestBuilder.build());
    }
}