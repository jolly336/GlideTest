package com.nelson.glidetest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.nelson.glidetest.BaseActivity;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.ActivityTransformationBinding;
import com.nelson.glidetest.model.ResourceConfig;
import com.nelson.glidetest.network.okhttp.GlideApp;
import com.nelson.glidetest.transformation.BlurTransformation;
import com.nelson.glidetest.transformation.RotateTransformation;

/**
 * 自定义转换 Transformations，转换图片的任意属性：尺寸，范围，颜色，像素位置等等！
 * Glide已包含了2个transformation，即：fitCenter和centerCrop
 *
 * glide-transformations库为app提供了多种多样的实现。
 *
 * @see <a href="https://github.com/wasabeef/glide-transformations">
 *
 * Created by Nelson on 2018/4/13.
 */

public class TransformationActivity extends BaseActivity {

    private ActivityTransformationBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Transformations");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transformation);

        showBlurTransformation();

        showMultiTransformation();

        showThirdTransformation();
    }

    private void showBlurTransformation() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[8])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .transform(new BlurTransformation(this))
                // bitmapTransform只能用于bitmap的转换
                //.bitmapTransform(new BlurTransformation(this))
                .into(mBinding.ivBlur);
    }

    /**
     * 运用多种转换，提示：当你使用了转换后你就不能使用.centerCrop()或.fitCenter()了！！！
     */
    private void showMultiTransformation() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[8])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                // 确保只调用了一次.transform()或.bitmapTransform()，否则，之前的配置就会被覆盖掉的！
                .transforms(new BlurTransformation(this), new RotateTransformation())
                .into(mBinding.ivMulti);

    }

    /**
     * 使用glide-transformations库来做转换
     */
    private void showThirdTransformation() {
        GlideApp.with(this)
                .load(ResourceConfig.IMAGE_REMOTE_URLS[8])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .transform(new jp.wasabeef.glide.transformations.BlurTransformation(this, 25, 2))
                .into(mBinding.ivThird);
    }

}
