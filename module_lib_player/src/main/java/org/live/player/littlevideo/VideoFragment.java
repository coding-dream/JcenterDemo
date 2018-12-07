package org.live.player.littlevideo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.live.player.AbstractVideoController;
import org.live.player.R;
import org.live.player.SimpleController;
import org.live.player.VideoItem;
import org.live.player.ZZVideoPlayer;

/**
 * Created by wl on 2018/12/6.
 */
public class VideoFragment extends Fragment {

    private ZZVideoPlayer zzVideoPlayer;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        zzVideoPlayer = rootView.findViewById(R.id.zzVideoPlayer);
        progressBar = rootView.findViewById(R.id.progressBar);
        autoPlay();
    }

    private void autoPlay() {
        VideoItem viewItem = new VideoItem();
        viewItem.setUrl("http://183.60.197.31/17/e/b/h/s/ebhszyahvgiogerfngngcbftwrssxx/hc.yinyuetai.com/BF4901677D3F3BB8AC36A8715B85859A.mp4");
        viewItem.setTitle("测试视频");
        viewItem.setCover("http://img1.c.yinyuetai.com/video/mv/181127/0/b8af67fe6265f1497a6afb271a21ecf6_240x135.jpg");

        zzVideoPlayer.setPlayerType(ZZVideoPlayer.TYPE_IJK);
        zzVideoPlayer.setController(new SimpleController(getActivity()), new AbstractVideoController.VideoCallback() {
            @Override
            public void onBack() {

            }

            @Override
            public void onPrepared() {
                progressBar.setVisibility(View.GONE);
            }
        });
        zzVideoPlayer.setUp(viewItem, null, false);
        zzVideoPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        zzVideoPlayer.pause();
    }
}
