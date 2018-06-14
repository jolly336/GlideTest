package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityGlide4Binding;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;

/**
 * Glide4.x 新API展示
 *
 * Created by Nelson on 2018/6/14.
 */
public class Glide4Activity extends BaseActivity {

    private ActivityGlide4Binding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("CustomImageSize");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_glide4);

        showNormalImageLoad();

        showImageLoadWithExtension();
    }

    /**
     * 使用Glide4.x新Api来加载网络图片
     */
    private void showNormalImageLoad() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error);

        String remoteUrl = ResourceConfig.IMAGE_REMOTE_URLS[0];
        Glide.with(this)
                .load(remoteUrl)
                .apply(options)
                .into(mBinding.ivNormal);
    }


    /**
     * 使用Glide4.x定制自己的API，需要借助{@link com.bumptech.glide.load.engine.GlideException}和{@link com.bumptech.glide.annotation.GlideOption}
     * 这两个注解，创建一个我们自定义的扩展类
     */
    private void showImageLoadWithExtension() {

        String remoteUrl = ResourceConfig.IMAGE_REMOTE_URLS[1];

        GlideApp.with(this)
                .load(remoteUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .cacheSource()
                .into(mBinding.ivExtension);
    }


}
