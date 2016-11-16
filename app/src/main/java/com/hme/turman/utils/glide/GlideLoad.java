package com.hme.turman.utils.glide;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.hme.turman.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by diaoqf on 2016/11/16.
 */

public class GlideLoad {

    public static DrawableRequestBuilder<String> init(Activity activity) {
        return Glide.with(activity)
                .fromString()
                .skipMemoryCache(Build.VERSION.SDK_INT < 21)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .clone();
    }

    public static DrawableRequestBuilder<String> init(Fragment fragment) {
        return Glide.with(fragment)
                .fromString()
                .skipMemoryCache(Build.VERSION.SDK_INT < 21)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .clone();
    }

    public static class Builder{
        private int placeholder = 0;
        private int error = 0;
        private boolean fitCenter;
        private final DrawableRequestBuilder<String> requestBuilder;
        private final String url;
        private ImageView imageView;

        public Builder(DrawableRequestBuilder<String> requestBuilder, String url) {
            this.requestBuilder = requestBuilder;
            this.url = url;
            placeHolder(R.drawable.default_portrait);
            error(R.drawable.default_portrait);
        }

        public Builder(DrawableRequestBuilder<String> requestBuilder, String url, int placeholderId, int errorId){
            this.requestBuilder = requestBuilder;
            this.url = url;
            placeHolder(placeholderId);
            error(errorId);
        }

        /**
         * 启用fitCenter,默认centerCrop
         *
         * @return Builder
         */
        public Builder fitCenter() {
            fitCenter = true;
            return this;
        }

        public Builder into(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder placeHolder(@DrawableRes int placeholderId) {
            this.placeholder = placeholderId;
            return this;
        }

        public Builder error(@DrawableRes int error) {
            this.error = error;
            return this;
        }

    }

    public static void load(Builder builder){
        if (builder.fitCenter) {
            builder.requestBuilder
                    .load(builder.url)
                    .dontAnimate()
                    .placeholder(builder.placeholder)
                    .error(builder.error)
                    .listener(new LogListener<>())
                    .fitCenter()
                    .into(builder.imageView);
        }else {
            builder.requestBuilder
                    .load(builder.url)
                    .dontAnimate()
                    .placeholder(builder.placeholder)
                    .error(builder.error)
                    .listener(new LogListener<>())
                    .centerCrop()
                    .into(builder.imageView);
        }
    }

    /**
     * 保存图片
     * @param context
     * @param file
     * @param url
     * @return
     */
    public static boolean downLoadImage(Activity context, File file, String url){
        try {
            file = Glide.with(context)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            if (file!=null){
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化圆形加载
     *
     * @return
     */
    public static BitmapTypeRequest<String> initRound(Activity activity) {
        return Glide
                .with(activity)
                .from(String.class)
                .asBitmap();
    }

    /**
     * 初始化圆形加载
     *
     * @return
     */
    public static BitmapTypeRequest<String> initRound(Fragment fragment) {
        return Glide
                .with(fragment)
                .from(String.class)
                .asBitmap();
    }

}
