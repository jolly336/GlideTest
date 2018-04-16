package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityCustomImageSizeBinding;
import com.nelson.glidetest.databinding.ActivityHttpsBinding;
import com.nelson.glidetest.glidemodule.CustomImageSizeModel;
import com.nelson.glidetest.model.ResourceConfig;

/**
 * self-signed 自签名 HTTPS认证
 *
 * Created by Nelson on 2018/4/13.
 */

public class HttpsActivity extends AppCompatActivity {

    private ActivityHttpsBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Https");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_https);
        showHttps();
    }

    private void showHttps() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_HTTPS_URL)  // Https image hreflink
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(mBinding.ivHttps);
    }

}
