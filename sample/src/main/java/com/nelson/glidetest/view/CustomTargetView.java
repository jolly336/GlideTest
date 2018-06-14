package com.nelson.glidetest.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * 展示Glide支持加载图片到自定义view中，因为并没有办法知道图片应该在哪里被设置。Glide使用ViewTarget更容易实现。
 *
 * Created by Nelson on 2018/1/19.
 */

public class CustomTargetView extends FrameLayout {

    private final ViewTarget<CustomTargetView, Drawable> mViewTarget;

    public CustomTargetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewTarget = new ViewTarget<CustomTargetView, Drawable>(this) {
            @Override
            public void onResourceReady(@NonNull Drawable resource,
                    @Nullable Transition<? super Drawable> transition) {
                CustomTargetView customTargetView = getView();
                customTargetView.setImageAsBackground(resource);
            }
        };
    }

    public ViewTarget<CustomTargetView, Drawable> getTarget() {
        return mViewTarget;
    }

    public void setImageAsBackground(Drawable drawable) {
        setBackground(drawable);
    }
}
