package com.nelson.glidetest.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityLoadProgressBinding;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;
import com.nelson.glidetest.network.okhttp.ProgressInterceptor;
import com.nelson.glidetest.network.okhttp.ProgressListener;

/**
 * Load image with {@link ProgressDialog}
 *
 * Created by Nelson on 2018/1/22.
 */

public class LoadProgressActivity extends BaseActivity {

    private static final String TAG = "LoadProgressActivity";

    private static final String URL = ResourceConfig.IMAGE_REMOTE_URLS[0];

    private ActivityLoadProgressBinding mBinding;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_load_progress);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("loading...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Just for test
        mBinding.ivImage.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onStart: width: " + mBinding.ivImage.getWidth() + ", height: "
                        + mBinding.ivImage.getHeight());
            }
        });

        ViewTreeObserver observer = mBinding.ivImage.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBinding.ivImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.e(TAG, "ViewTreeObserver: width: " + mBinding.ivImage.getWidth() + ", height: "
                        + mBinding.ivImage.getHeight());
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // Just for test
        if (hasFocus) {
            Log.e(TAG, "onWindowFocusChanged: width: " + mBinding.ivImage.getWidth() + ", height: "
                    + mBinding.ivImage.getHeight());
        }
    }

    public void loadImage(View view) {
        ProgressInterceptor.addListener(URL, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                mProgressDialog.setProgress(progress);
            }
        });

        GlideApp.with(this)
                .load(URL)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontTransform()
                //.override(ViewTarget.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.net_error_img)
                //into(mBinding.ivImage);
                .into(new DrawableImageViewTarget(mBinding.ivImage) {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        mProgressDialog.show();
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        mProgressDialog.dismiss();
                        ProgressInterceptor.removeListener(URL);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                            @Nullable Transition<? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        mProgressDialog.dismiss();
                        ProgressInterceptor.removeListener(URL);
                    }
                });
    }
}
