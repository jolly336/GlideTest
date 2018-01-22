package com.zihuatanejo.glidetest;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.zihuatanejo.glidetest.databinding.ActivityLoadProgressBinding;
import com.zihuatanejo.glidetest.okhttp.ProgressInterceptor;
import com.zihuatanejo.glidetest.okhttp.ProgressListener;

/**
 * Load image with {@link ProgressDialog}
 *
 * Created by Nelson on 2018/1/22.
 */

public class LoadProgressActivity extends AppCompatActivity {

    private static final String URL = "http://omdtn071e.bkt.clouddn.com/anthony-intraversato-257182.jpg";

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

    public void loadImage(View view) {
        ProgressInterceptor.addListener(URL, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                mProgressDialog.setProgress(progress);
            }
        });

        Glide.with(this)
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontTransform()
                //.override(ViewTarget.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.net_error_img)
                //into(mBinding.ivImage);
                .into(new GlideDrawableImageViewTarget(mBinding.ivImage) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        mProgressDialog.show();
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource,
                            GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        mProgressDialog.dismiss();
                        ProgressInterceptor.removeListener(URL);
                    }
                });
    }
}
