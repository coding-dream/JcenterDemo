package org.live.test.activity

import android.content.Context
import android.support.v4.view.ViewPager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_banner.*
import org.live.test.R
import org.live.test.base.BaseActivity

/**
 * Created by wl on 2018/11/26.
 *
 * 参考: HomeFragmentV2, TopDelegate
 */
class BannerActivity : BaseActivity() {

    override fun initView() {
        val bannerList = mutableListOf<String>()
        bannerList.add("https://upload-images.jianshu.io/upload_images/4155409-78639160b2cfdec5")
        bannerList.add("https://upload-images.jianshu.io/upload_images/4155409-7adf342edbcd8481")
        bannerList.add("https://upload-images.jianshu.io/upload_images/4155409-fc846b779cef95d8")
        bannerList.add("https://upload-images.jianshu.io/upload_images/4155409-7adf342edbcd8481")

        updateBanner(bannerList)
    }

    private fun updateBanner(bannerList: MutableList<String>) {

        pic_banner.setImages(bannerList)
                .setImageLoader(object : ImageLoader() {
                    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                        Glide.with(context)
                                .load(path)
                                .into(imageView)
                    }
                })
                // 设置内嵌indicator的位置
                .setIndicatorGravity(BannerConfig.CENTER)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .start()
        pic_banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        // 如果使用自己的indicator
        // pic_banner.updateBannerStyle(BannerConfig.NOT_INDICATOR)

        // 监听器用于设置第三方indicator
        pic_banner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
            }

        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_banner
    }
}