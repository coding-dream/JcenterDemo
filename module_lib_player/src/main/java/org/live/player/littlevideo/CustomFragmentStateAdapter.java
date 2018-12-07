package org.live.player.littlevideo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by wl on 2018/12/6.
 */
public class CustomFragmentStateAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    public CustomFragmentStateAdapter(FragmentManager fm ,List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}