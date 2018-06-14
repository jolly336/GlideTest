package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityCacheBinding;
import com.nelson.glidetest.glidemodule.CustomCachingGlideModule;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;

/**
 * Showing memory,disk,and other custom defined cache size or directory
 * {@link CustomCachingGlideModule}.
 *
 * Created by Nelson on 2018/4/13.
 */

public class CacheActivity extends BaseActivity {

    private ActivityCacheBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cache);
        setTitle("Cache");

        // memory cache
        showMemoryCache();

        // disk cache
        showDiskCache();
    }

    private void showMemoryCache() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[3])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .skipMemoryCache(true)
                .into(mBinding.ivMemory);
    }

    private void showDiskCache() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[3])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                // no disk cache
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                // cache all disk data,contains result and source data
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                // cache source data except result
                //.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mBinding.ivDisk);
    }
}
