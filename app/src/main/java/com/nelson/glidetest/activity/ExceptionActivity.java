package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityExceptionBinding;
import com.nelson.glidetest.model.ResourceConfig;

/**
 * 异常：调试和错误处理
 *
 * Glide的{@link com.bumptech.glide.request.GenericRequest}类提供了一个方法去设置log的级别，不幸的是，在生产过程中，使用这个类不容易。
 * 然而，有一个非常简单的方法去获得Glide的调试日志。要做的就是通过adb的shell来激活。使用如下命令：
 * adb shell setprop log.tag.GenericRequest DEBUG
 *
 * VERBOSE
 * DEBUG
 * INFO
 * WARN
 * ERROR
 *
 * 如果图像不存在，会输出这样的Log:
 * D/GenericRequest: load failed
 * D/GenericRequest: java.io.IOException: Request failed 404: Not Found
 *
 * Created by Nelson on 2018/4/13.
 */

public class ExceptionActivity extends AppCompatActivity {

    private ActivityExceptionBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Exception");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_exception);

        showErrorListener();
    }

    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                boolean isFirstResource) {
            //  todo log exception 打Log
            // important to return false so the error placeholder can be placed.
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model,
                Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

    /**
     * 常规异常日志记录
     */
    private void showErrorListener() {
        Glide.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[7])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .listener(requestListener)
                .into(mBinding.ivListener);
    }
}
