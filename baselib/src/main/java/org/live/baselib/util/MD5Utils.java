package org.live.baselib.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * <p>Created by LeonLee on 2014/9/4 17:21.</p>
 * <p>
 * MD5工具处理类
 * <p>
 * <ul>
 * <li>{@link #getMd5(byte[])} 获取字节数组的MD5值</li>
 * <li>{@link #getMd5(String)} 获取字符串的MD5值</li>
 * <li>{@link #getMd5(java.io.File)} 获取文件的MD5值</li>
 * </ul>
 */
public class MD5Utils {

    private MD5Utils() {
    }

    /**
     * 获取字节数组的MD5值
     *
     * @param arrays 字节数组
     */
    public static String getMd5(byte[] arrays) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(arrays);
            return StringUtils.bytesToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取字符串的MD5值
     *
     * @param src 要获取的字符串
     */
    public static String getMd5(String src) {
        if (!TextUtils.isEmpty(src)) {
            return getMd5(src.getBytes());
        }
        return "";
    }

    /**
     * 获取文件的MD5值
     *
     * @param file 要获取的文件
     */
    public static String getMd5(File file) {
        String s = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY,
                    0, file.length());

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(byteBuffer);
            s = StringUtils.bytesToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return s;
    }

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }
}
