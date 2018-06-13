package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityCrossFadeBinding;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;

/**
 * Created by Nelson on 2018/4/13.
 */

public class CrossFadeActivity extends BaseActivity {

    private ActivityCrossFadeBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cross_fade);
        setTitle("CrossFade");

        // placeHolder + error
        showPlaceHolder();

        // crossFade
        showCrossFade();

        // animate
        showAnimate();
    }

    private void showPlaceHolder() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[1])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivPlaceholder);
    }

    private void showCrossFade() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[1])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                //.transition(DrawableTransitionOptions.withCrossFade())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(mBinding.ivCrossfade);
    }


    private void showAnimate() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[1])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .dontAnimate()
                .into(mBinding.ivAnimate);
    }

}
