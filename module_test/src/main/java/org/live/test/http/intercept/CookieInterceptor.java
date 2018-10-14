package org.live.test.http.intercept;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookieInterceptor implements Interceptor {

    private static String cookie;


    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (cookie != null) {
            request = request.newBuilder().addHeader("Cookie", cookie).build();
        }
        Response response = chain.proceed(request);
        String cookieStr = response.header("Set-Cookie");
        if (cookieStr != null) {
            String[] cookies = cookieStr.split(";");
            if (cookies.length != 0) {
                cookie = cookies[0];
            }
        }
        return response;
    }
}