package org.live.test.http.intercept;

import com.orhanobut.logger.Logger;

import org.live.test.App;
import org.live.test.http.ex.NoConnectivityException;
import org.live.test.http.util.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (NetworkUtils.isNetworkConected(App.getAppContext())) {
            return chain.proceed(chain.request());
        } else {
            throw new NoConnectivityException();
        }
    }
}
