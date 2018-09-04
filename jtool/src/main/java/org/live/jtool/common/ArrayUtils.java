package org.live.jtool.common;

import java.util.List;

/**
 * Created by Domen、on 2018/1/8.
 */

public class ArrayUtils {

    public static boolean inArray(List list, int position) {
        return list != null && !(position < 0 || position >= list.size());
    }

    public static <T> T safeGet(List<T> list, int position) {
        return safeGet(list, position, null);
    }

    public static <T> T safeGet(List<T> list, int position, T defaultValue) {
        return inArray(list, position) ? list.get(position) : defaultValue;
    }
}