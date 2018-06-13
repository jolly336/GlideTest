package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityCustomImageSizeBinding;
import com.nelson.glidetest.glidemodule.CustomImageSizeModel;
import com.nelson.glidetest.glidemodule.CustomImageSizeModelFuture;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;

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

        GlideApp.with(this)
                .load(customImageRequest)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivCustomAll);
    }

    /**
     * 动态使用ModelLoader
     *
     * The using() API was removed in Glide 4 to encourage users to register their components once with a AppGlideModule to avoid object re-use.
     * Rather than creating a new ModelLoader each time you load an image, you register it once in an AppGlideModule
     * and let Glide inspect your model (the object you pass to load()) to figure out when to use your registered ModelLoader.
     *
     * To make sure you only use your ModelLoader for certain models, implement handles() as shown above to inspect each model and return true only if your ModelLoader should be used.
     */
    private void showCustomImageSizeUsingSingleRequest() {
        String baseImageUrl = ResourceConfig.IMAGE_HTTPS_URL;
        CustomImageSizeModel customImageRequest = new CustomImageSizeModelFuture(baseImageUrl);

        GlideApp.with(this)
                //.using(new CustomImageSizeUrlLoader(this))
                .load(customImageRequest)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivCustomSingle);
    }

}
