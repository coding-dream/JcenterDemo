package org.live.player;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wl on 2018/11/29.
 *
 * 简单播放器用例, 自定义Controller
 */
public class SimpleVideoActivity extends AppCompatActivity {

    IZZVideoPlayer videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_simple);
        videoPlayer = findViewById(R.id.customVideoPlayer);

        VideoItem viewItem = new VideoItem();
        viewItem.setUrl("http://183.60.197.31/17/e/b/h/s/ebhszyahvgiogerfngngcbftwrssxx/hc.yinyuetai.com/BF4901677D3F3BB8AC36A8715B85859A.mp4");
        viewItem.setTitle("测试视频");
        viewItem.setCover("http://img1.c.yinyuetai.com/video/mv/181127/0/b8af67fe6265f1497a6afb271a21ecf6_240x135.jpg");

        videoPlayer.setPlayerType(ZZVideoPlayer.TYPE_IJK);
        videoPlayer.setController(new SimpleController(this), null);
        videoPlayer.setUp(viewItem, null, false);
        videoPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 防止ButterKnife解绑导致的空指针
        if (videoPlayer != null) {
            videoPlayer.release();
        }
    }
}