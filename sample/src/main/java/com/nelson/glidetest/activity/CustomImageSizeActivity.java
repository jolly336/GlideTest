package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityCustomImageSizeBinding;
import com.nelson.glidetest.glidemodule.CustomImageSizeModel;
import com.nelson.glidetest.glidemodule.CustomImageSizeModelFuture;
import com.nelson.glidetest.glidemodule.CustomImageSizeUrlLoader;
import com.nelson.glidetest.model.ResourceConfig;

/**
 * 用自定义尺寸优化加载的图片(创建一个新的接口，来考虑增加宽度和高度)
 *
 * Created by Nelson on 2018/4/13.
 */

public class CustomImageSizeActivity extends BaseActivity {

    private ActivityCustomImageSizeBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("CustomImageSize");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_custom_image_size);

        //showCustomImageSize();

        showCustomImageSizeUsingSingleRequest();
    }

    /**
     * 自定义尺寸优化加载的图片(全局使用，需要在Manifest文件中注册GlideModel)
     */
    private void showCustomImageSize() {
        String baseImageUrl = ResourceConfig.IMAGE_HTTPS_URL;
        CustomImageSizeModel customImageRequest = new CustomImageSizeModelFuture(baseImageUrl);

        Glide.with(this)
                .load(customImageRequest)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivCustomAll);
    }

    /**
     * 动态使用ModelLoader
     */
    private void showCustomImageSizeUsingSingleRequest() {
        String baseImageUrl = ResourceConfig.IMAGE_HTTPS_URL;
        CustomImageSizeModel customImageRequest = new CustomImageSizeModelFuture(baseImageUrl);

        Glide.with(this)
                .using(new CustomImageSizeUrlLoader(this))
                .load(customImageRequest)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivCustomSingle);
    }

}
