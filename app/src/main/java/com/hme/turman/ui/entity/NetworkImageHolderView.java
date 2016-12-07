package com.hme.turman.ui.entity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.hme.turman.R;

/**
 * Created by diaoqf on 2016/12/7.
 */

public class NetworkImageHolderView implements Holder<String> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        imageView.setImageResource(R.drawable.default_portrait);
        Glide.with(context)
                .load(data)
                .into(imageView);
    }
}
