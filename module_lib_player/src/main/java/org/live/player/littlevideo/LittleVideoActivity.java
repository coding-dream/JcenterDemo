package org.live.player.littlevideo;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import org.live.player.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl on 2018/12/6.
 */
public class LittleVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_little_video);

        SwipeFlingScaleLayout swipeFlingScaleLayout = findViewById(R.id.swipeFlingScaleLayout);

        List fragmentList = new ArrayList<>();
        fragmentList.add(new VideoFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new VideoFragment());

        FragmentStatePagerAdapter fragmentStatePagerAdapter = new CustomFragmentStateAdapter(getSupportFragmentManager(), fragmentList);
        swipeFlingScaleLayout.setViewPagerData(fragmentStatePagerAdapter);

        swipeFlingScaleLayout.setSwipeCallback(new SwipeFlingScaleLayout.SwipeCallback() {
            @Override
            public void dismiss(float scrollX, float scrollY, float width, float height) {
                doFinishAnimator();

                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void doFinishAnimator() {

    }
}