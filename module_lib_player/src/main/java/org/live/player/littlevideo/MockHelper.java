package org.live.player.littlevideo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl on 2018/12/5.
 */
public class MockHelper {

    public static List<String> getDatas() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("menu: " + i);
        }
        return datas;
    }
}
