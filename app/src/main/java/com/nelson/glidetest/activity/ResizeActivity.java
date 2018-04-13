package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityResizeBinding;
import com.nelson.glidetest.model.ResourceConfig;

/**
 * Created by Nelson on 2018/4/13.
 */

public class ResizeActivity extends AppCompatActivity {

    private ActivityResizeBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_resize);
        setTitle("Resize");

        // override
        showOverride();

        // centerCrop
        showCenterCrop();

        // fitCenter
        showFitCenter();
    }

    private void showOverride() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                // Resize the image to these dimensions(in pixel).does not respect aspect ratio
                //.override(100, 100)
                // 将图片尺寸指定为原始大小，效果等同于dontTransform()取消图片变换
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(mBinding.ivOverride);
    }

    private void showCenterCrop() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                // CenterCrop()是一个裁剪技术，即缩放图像让它填充到 ImageView 界限内并且裁剪额外的部分。ImageView 可能会完全填充，但图像可能不会完整显示
                .centerCrop()
                .into(mBinding.ivCentercrop);
    }

    private void showFitCenter() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                // fitCenter() 是裁剪技术，即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView
                // 注意：ImageView默认的scaleType是FIT_CENTER
                .fitCenter()
                .into(mBinding.ivFitcenter);
    }

}
