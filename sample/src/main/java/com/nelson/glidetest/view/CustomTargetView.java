package com.nelson.glidetest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * 展示Glide支持加载图片到自定义view中，因为并没有办法知道图片应该在哪里被设置。Glide使用ViewTarget更容易实现。
 *
 * Created by Nelson on 2018/1/19.
 */

public class CustomTargetView extends FrameLayout {

    private final ViewTarget<CustomTargetView, GlideDrawable> mViewTarget;

    public CustomTargetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewTarget = new ViewTarget<CustomTargetView, GlideDrawable>(this) {
            @Override
            public void onResourceReady(GlideDrawable resource,
                    GlideAnimation<? super GlideDrawable> glideAnimation) {
                CustomTargetView customTargetView = getView();
                customTargetView.setImageAsBackground(resource);
            }
        };
    }

    public ViewTarget<CustomTargetView, GlideDrawable> getTarget() {
        return mViewTarget;
    }

    public void setImageAsBackground(GlideDrawable drawable) {
        setBackground(drawable);
    }
}
