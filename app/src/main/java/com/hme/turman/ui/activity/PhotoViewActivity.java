package com.hme.turman.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by diaoqf on 2016/11/16.
 */

public class PhotoViewActivity extends BaseActivity {
    public static final String IMAGE_URL = "image_url";
    public static final String CURRENT_PAGE = "current_page";

    @BindView(R.id.pager)
    ViewPager pager;

    private ArrayList<String> img_urls;

    @Override
    protected void init(Bundle savedInstanceState) {
        img_urls = getIntent().getStringArrayListExtra(IMAGE_URL);
        pager.setCurrentItem(getIntent().getIntExtra(CURRENT_PAGE,0));
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return img_urls.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(PhotoViewActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                try {
                    view.setImageBitmap(Glide.with(PhotoViewActivity.this)
                    .load(img_urls.get(position))
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                    .get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_photo_view;
    }
}
