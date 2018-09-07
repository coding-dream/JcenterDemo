package org.live.baselib.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URl编解码类，简单对系统api进行封装，解决把空格转为+号，导致某些接口处理异常的问题
 *
 * @author chenys
 *         <p>
 *         <ul>
 *         <li>{@link #encode(String)} 对字符进行Url转码</li>
 *         <li>{@link #encode(String, String)} 对字符进行Url转码</li>
 *         <li>{@link #escape(String)} 对字符串进行转义或编码</li>
 *         <li>{@link #encode(String)} 解码</li>
 *         <li>{@link #encode(String, String)} 解码</li>
 *         </ul>
 */
public class UrlCodecUtils {

    private UrlCodecUtils() {
    }

    /**
     * 对字符进行Url转码
     *
     * @param str 要转码的字符
     */
    @SuppressWarnings("deprecation")
    public static String encode(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return URLEncoder.encode(str).replace("+", "%20");
    }

    /**
     * 对字符进行Url转码
     *
     * @param str 要转码的字符
     * @param enc 字符编码
     */
    public static String encode(String str, String enc) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, enc).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            return encode(str);
        }
    }

    /**
     * 对字符串进行转义或编码
     *
     * @param src
     */
    public static String escape(String src) {
        int i;
        char j;
        StringBuilder tmp = new StringBuilder(20);
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
                tmp.append(j);
            } else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16)); //16进制
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * 解码
     *
     * @param str 要解码的字符
     */
    @SuppressWarnings("deprecation")
    public static String decode(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return URLDecoder.decode(str);
    }

    /**
     * 解码
     *
     * @param str 要解码的字符
     * @param enc 字符编码
     */
    public static String decode(String str, String enc) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLDecoder.decode(str, enc);
        } catch (Exception e) {
            return "";
        }
    }
}
