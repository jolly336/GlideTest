package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityThumbnailBinding;
import com.nelson.glidetest.model.ResourceConfig;

/**
 * Two ways to load image with thumbnail
 *
 * Created by Nelson on 2018/4/13.
 */

public class ThumbnailActivity extends BaseActivity {

    private ActivityThumbnailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thumbnail");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_thumbnail);

        // simple thumbnail
        showSizeMultiplier();

        // thumbnail request
        showThumbnailRequest();
    }

    /**
     * 缩略图用法一，使用thumbnail()函数，这个请求和原图请求是同一个请求
     *
     * 缩略图，先显示原图的10%的大小，等到原始图像到达后，会抹除缩略图，显示原始图片
     */
    private void showSizeMultiplier() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[5])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(0.1f)
                .into(mBinding.ivSizeMultiplier);
    }

    /**
     * 缩略图用法二，使用Glide请求作为参数，和原图请求独立开来，独自请求缩略图
     */
    private void showThumbnailRequest() {
        // setup Glide request without the into() method
        DrawableTypeRequest<String> thumbnailRequest = Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[4]);

        // pass the request as a parameter to the thumbnail request
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[5])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                // 磁盘四个枚举值：ALL 所有版本图片，NONE 不缓存图片，SOURCE 仅仅只缓存原来全分辨率图片，RESULT 仅仅缓存最终的图片(default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(thumbnailRequest)
                .into(mBinding.ivThumbnailRequest);
    }

}
