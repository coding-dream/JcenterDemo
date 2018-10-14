package org.live.test.http.ex;

public class NoConnectivityException extends RuntimeException {

    public NoConnectivityException() {
        super("网络未连接，请检查网络设置！");
    }
}