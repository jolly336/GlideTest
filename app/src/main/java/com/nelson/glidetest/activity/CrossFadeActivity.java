package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityCrossFadeBinding;
import com.nelson.glidetest.model.ResourceConfig;

/**
 * Created by Nelson on 2018/4/13.
 */

public class CrossFadeActivity extends AppCompatActivity {

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
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivPlaceholder);
    }

    private void showCrossFade() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                //.crossFade()
                .crossFade(500)
                .into(mBinding.ivCrossfade);
    }


    private void showAnimate() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .dontAnimate()
                .into(mBinding.ivAnimate);
    }

}
