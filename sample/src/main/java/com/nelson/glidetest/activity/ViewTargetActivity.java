package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityViewtargetBinding;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;
import com.nelson.glidetest.view.CustomTargetView;

/**
 * 回调：SimpleTarget和ViewTarget用于自定义视图类
 *
 * Created by Nelson on 2018/4/13.
 */

public class ViewTargetActivity extends BaseActivity {

    private ActivityViewtargetBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ViewTarget");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_viewtarget);

        showSimpleTarget();

        showSimpleTargetWithSize();

        showCustomViewTarget();
    }

    // [注意：]确保你所声明的回调对象是作为一个字段对象的，这样就可以保护它被邪恶的Android垃圾回收机制回收；（Android垃圾回收移除了匿名内部类对象）
    private SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(@NonNull Bitmap resource,
                @Nullable Transition<? super Bitmap> transition) {
            mBinding.ivSimpleTarget.setImageBitmap(resource);
        }
    };

    /**
     * 方法一：Target接受结果
     */
    private void showSimpleTarget() {

        GlideApp.with(this)
                .asBitmap()
                .load(ResourceConfig.IMAGE_REMOTE_URLS[6])
                // 强制Glide返回一个Bitmap对象
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(target);
    }

    /**
     * 方法二：Target指定尺寸
     */
    private void showSimpleTargetWithSize() {
        // 注意：可在target构造参数里加强宽高，可在回调中声明指定它以节省内存和时间
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>(100, 100) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource,
                    @Nullable Transition<? super Bitmap> transition) {
                mBinding.ivSimpleTargetWithSize.setImageBitmap(resource);
            }
        };

        GlideApp.with(this.getApplicationContext()) // 生命周期之外的请求，使用application context，safer!!!
                .asBitmap()
                .load(ResourceConfig.IMAGE_REMOTE_URLS[6])
                // 强制Glide返回一个Bitmap对象
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(target);
    }

    /**
     * 方法三：自定义view
     */
    private void showCustomViewTarget() {

        ViewTarget<CustomTargetView, Drawable> target = new ViewTarget<CustomTargetView, Drawable>(
                mBinding.ivCustomTargetView) {
            @Override
            public void onResourceReady(@NonNull Drawable resource,
                    @Nullable Transition<? super Drawable> transition) {
                // 你也可以在回调中添加额外的工作，如，我们可以分析传入的Bitmap的主要颜色并设置十六进制值给TextView，我相信你应该已经有一些想法了。
                mBinding.ivCustomTargetView.setImageAsBackground(resource);
            }
        };

        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[6])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                // 自定义target在自定义空间中，可以直接获取
                //.into(mBinding.customTargetView.getTarget())
                .into(target);
    }

}
