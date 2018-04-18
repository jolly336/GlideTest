package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityPriorityBinding;
import com.nelson.glidetest.model.ResourceConfig;

/**
 * {@link Priority} Priorities for completing loads.If more than one load is queued at a time,the
 * load with the higher priority will be started first.
 *
 * Created by Nelson on 2018/4/13.
 */

public class PriorityActivity extends BaseActivity {

    private ActivityPriorityBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_priority);
        setTitle("Priority");

        showLowPriority();

        showNormalPriority();

        showHighPriority();

        showImmediatePriority();
    }

    private void showLowPriority() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[4])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .priority(Priority.LOW)
                .into(mBinding.ivLowPriority);
    }

    private void showNormalPriority() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[4])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .priority(Priority.NORMAL)
                .into(mBinding.ivNormalPriority);
    }

    private void showHighPriority() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[4])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .priority(Priority.HIGH)
                .into(mBinding.ivHighPriority);
    }


    private void showImmediatePriority() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[4])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .priority(Priority.IMMEDIATE)
                .into(mBinding.ivImmediatePriority);
    }
}
