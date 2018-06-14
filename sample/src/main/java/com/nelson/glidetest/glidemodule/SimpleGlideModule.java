package com.nelson.glidetest.glidemodule;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * 创建一个额外的类去定制 Glide。下一步是要全局的去声明这个类，让 Glide 知道它应该在哪里被加载和使用。
 * Glide 会扫描 AndroidManifest.xml 为 Glide module 的 meta 声明。
 * 因此，你必须在 AndroidManifest.xml 的 <application> 标签内去声明这个刚刚创建的 Glide module。
 *
 * 注意：
 * 你去定制 module 的话 Glide 会有这样一个优点：你可以同时声明多个 Glide module。
 * Glide 将会（没有特定顺序）得到所有的声明 module。因为你当前不能定义顺序，请确保定制不会引起冲突！
 *
 * Created by Nelson on 16/12/19.
 */
public class SimpleGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
            @NonNull Registry registry) {
        // nothing to do here
    }
}
