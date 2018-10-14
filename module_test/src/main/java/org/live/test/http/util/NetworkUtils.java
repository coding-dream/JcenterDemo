package org.live.test.http.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <p>Created by 李文龙(LeonLee) on 14-10-10.</p>
 * <p>
 * 网络操作的工具类
 * <p>
 * <ul>
 * <li>{@link #isNetworkConected(Context)} 网络是否可用</li>
 * <li>{@link #isWifiConnected(Context)} 是否wifi网络中</li>
 * <li>{@link #getLocalIpAddress()} 获取手机IP地址</li>
 * </ul>
 */
public class NetworkUtils {

    public static final int NETWORK_TYPE_NOT_AVAILABLE = -1;
    public static final int NETWORK_TYPE_WIFI = 0;
    public static final int NETWORK_TYPE_2G = 1;
    public static final int NETWORK_TYPE_3G = 2;
    public static final int NETWORK_TYPE_4G = 3;
    public static final int NETWORK_TYPE_UNKNWON = 4;

    public static final String NETWORK_TYPE_DESC_NOT_AVAILABLE = "NOT_AVAILABLE";
    public static final String NETWORK_TYPE_DESC_WIFI = "WIFI";
    public static final String NETWORK_TYPE_DESC_2G = "2G";
    public static final String NETWORK_TYPE_DESC_3G = "3G";
    public static final String NETWORK_TYPE_DESC_4G = "4G";
    public static final String NETWORK_TYPE_DESC_UNKNWON = "UNKNOWN";

    private NetworkUtils() {
    }

    public static boolean isMobileNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * 是否在wifi网络中
     *
     * @param context 上下文
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 网络是否可用
     *
     * @param context 上下文
     * @return
     */
    public static boolean isNetworkConected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isAvailable());
        }
        return false;
    }

    /**
     * 获取手机IP地址
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return "";
    }

    public static int getOriginNetworkType(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return NETWORK_TYPE_NOT_AVAILABLE;
        }
        return networkinfo.getType();
    }

    /**
     * 获取当前手机的网络类型
     *
     * @param ctx
     * @return
     */
    public static int getNetworkType(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return NETWORK_TYPE_NOT_AVAILABLE;
        }

        switch (networkinfo.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                return NETWORK_TYPE_WIFI;
            case ConnectivityManager.TYPE_MOBILE:
                switch (networkinfo.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                    case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                    case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NETWORK_TYPE_2G;
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NETWORK_TYPE_3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NETWORK_TYPE_4G;

                }
        }
        return NETWORK_TYPE_UNKNWON;
    }


    public static String getNetworkTypeDesc(Context ctx) {
        int type = getNetworkType(ctx);
        switch (type) {
            case NETWORK_TYPE_NOT_AVAILABLE:
                return NETWORK_TYPE_DESC_NOT_AVAILABLE;

            case NETWORK_TYPE_WIFI:
                return NETWORK_TYPE_DESC_WIFI;

            case NETWORK_TYPE_2G:
                return NETWORK_TYPE_DESC_2G;

            case NETWORK_TYPE_3G:
                return NETWORK_TYPE_DESC_3G;

            case NETWORK_TYPE_4G:
                return NETWORK_TYPE_DESC_4G;

            default:
                return NETWORK_TYPE_DESC_UNKNWON;
        }

    }


    public static int getNetworkType4Monitor(Context ctx) {
        int type = getNetworkType(ctx);
        switch (type) {
            case NETWORK_TYPE_NOT_AVAILABLE:
                return 5;

            case NETWORK_TYPE_WIFI:
                return 2;

            case NETWORK_TYPE_2G:
                return 1;

            case NETWORK_TYPE_3G:
                return 3;

            case NETWORK_TYPE_4G:
                return 4;

            default:
                return 5;
        }

    }


    /**
     * 检查互联网地址是否可以访问-使用DNS解析
     *
     * @param hostname 要检查的域名或IP
     * @param callback 检查结果回调（是否可以解析成功）{@see java.lang.Comparable<T>}
     */
    public static void isNetWorkAvailableOfDNS(final String hostname, final OnDNSCheckListener callback) {
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (callback != null) {
                    String extra = msg.getData().getString("extra");
                    callback.onDNSCheck(msg.arg1 == 0,extra);
                }
            }

        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                try {
                    DNSParse parse = new DNSParse(hostname);
                    Thread thread = new Thread(parse);
                    thread.start();
                    thread.join(3 * 1000); // 设置等待DNS解析线程响应时间为3秒
                    InetAddress resCode = parse.get(); // 获取解析到的IP地址
                    if (resCode == null) {
                        msg.arg1 = -1;
                        bundle.putString("extra", "time out");
                    } else {
                        msg.arg1 = 0;
                        bundle.putString("extra", resCode.getHostAddress());
                    }
                } catch (Exception e) {
                    msg.arg1 = -2;
                    if (null!=e){
                        bundle.putString("extra", e.getMessage());
                    }
                } finally {
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }

    public interface OnDNSCheckListener {
        void onDNSCheck(boolean available, String extra);
    }

    /**
     * DNS解析线程
     */
    private static class DNSParse implements Runnable {
        private String hostname;
        private InetAddress address;

        public DNSParse(String hostname) {
            this.hostname = hostname;
        }

        public void run() {
            try {
                set(InetAddress.getByName(hostname));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public synchronized void set(InetAddress address) {
            this.address = address;
        }

        public synchronized InetAddress get() {
            return address;
        }
    }

}