package org.live.test.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.live.test.R;

import java.util.List;

/**
 * Created by wl on 2018/12/5.
 */
class SimplePagerAdapter extends PagerAdapter {

    private List<String> datas;

    public SimplePagerAdapter(List<String> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View cacheView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager_custom, container, false);
        container.addView(cacheView);
        return cacheView ;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
