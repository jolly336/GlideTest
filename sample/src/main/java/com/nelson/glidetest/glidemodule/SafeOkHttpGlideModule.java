package com.nelson.glidetest.glidemodule;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.nelson.glidetest.network.SafeOkHttpClient;
import com.nelson.glidetest.network.SafeOkHttpUrlLoader.Factory;
import com.nelson.glidetest.network.UnsafeOkHttpUrlLoader;
import java.io.InputStream;

/**
 * 自定义Module实例：接受自签名证书的HTTPS
 *
 * 这个类的细节，应该对于这个Glide系统应该有一个大概的理解，Glide能去替换内部的工厂组件
 *
 * Created by Nelson on 16/12/19.
 */
public class SafeOkHttpGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

        glide.register(GlideUrl.class, InputStream.class, new UnsafeOkHttpUrlLoader.Factory(
                SafeOkHttpClient.getsafeOkHttpClient(context)));
    }
}
